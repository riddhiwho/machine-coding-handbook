import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class VendingMachine {
    private final Map<String, InventoryItem> inventory;
    private final Map<Currency, Integer> cashRegister;
    private final ReentrantLock inventoryLock;
    private final ReentrantLock cashLock;
    
    public VendingMachine() {
        this.inventory = new ConcurrentHashMap<>();
        this.cashRegister = new ConcurrentHashMap<>();
        this.inventoryLock = new ReentrantLock();
        this.cashLock = new ReentrantLock();
        
        // Initialize cash register with some change
        initializeCashRegister();
    }
    
    private void initializeCashRegister() {
        cashRegister.put(Currency.PENNY, 100);
        cashRegister.put(Currency.NICKEL, 50);
        cashRegister.put(Currency.DIME, 50);
        cashRegister.put(Currency.QUARTER, 50);
        cashRegister.put(Currency.DOLLAR_COIN, 20);
        cashRegister.put(Currency.ONE_DOLLAR, 10);
        cashRegister.put(Currency.FIVE_DOLLAR, 5);
        cashRegister.put(Currency.TEN_DOLLAR, 2);
        cashRegister.put(Currency.TWENTY_DOLLAR, 1);
    }
    
    // Add or restock products
    public void addProduct(Product product, int quantity) {
        inventoryLock.lock();
        try {
            InventoryItem existingItem = inventory.get(product.getId());
            if (existingItem != null) {
                existingItem.setQuantity(existingItem.getQuantity() + quantity);
            } else {
                inventory.put(product.getId(), new InventoryItem(product, quantity));
            }
            System.out.println("Added/Restocked: " + product.getName() + " (Quantity: " + 
                             inventory.get(product.getId()).getQuantity() + ")");
        } finally {
            inventoryLock.unlock();
        }
    }
    
    // Display available products
    public void displayProducts() {
        inventoryLock.lock();
        try {
            System.out.println("\n=== Available Products ===");
            for (InventoryItem item : inventory.values()) {
                String status = item.isInStock() ? 
                    "In Stock (" + item.getQuantity() + ")" : "Out of Stock";
                System.out.println(item.getProduct() + " - " + status);
            }
            System.out.println("========================\n");
        } finally {
            inventoryLock.unlock();
        }
    }
    
    // Purchase a product
    public TransactionResult purchaseProduct(String productId, Map<Currency, Integer> payment) 
            throws InsufficientFundsException, OutOfStockException, 
                   InvalidProductException, InsufficientChangeException {
        
        // Validate product exists
        InventoryItem item = inventory.get(productId);
        if (item == null) {
            throw new InvalidProductException("Product not found: " + productId);
        }
        
        Product product = item.getProduct();
        
        // Calculate total payment
        BigDecimal totalPaid = calculateTotalPayment(payment);
        
        // Check if payment is sufficient
        if (totalPaid.compareTo(product.getPrice()) < 0) {
            throw new InsufficientFundsException(
                String.format("Insufficient funds. Required: $%.2f, Provided: $%.2f", 
                            product.getPrice(), totalPaid));
        }
        
        // Calculate change needed
        BigDecimal changeNeeded = totalPaid.subtract(product.getPrice());
        
        // Use locks to ensure thread safety
        inventoryLock.lock();
        try {
            cashLock.lock();
            try {
                // Check stock availability
                if (!item.isInStock()) {
                    throw new OutOfStockException("Product out of stock: " + product.getName());
                }
                
                // Check if we can make change
                Map<Currency, Integer> changeToReturn = new HashMap<>();
                if (changeNeeded.compareTo(BigDecimal.ZERO) > 0) {
                    changeToReturn = calculateChange(changeNeeded);
                }
                
                // All checks passed, process the transaction
                // Add payment to cash register
                for (Map.Entry<Currency, Integer> entry : payment.entrySet()) {
                    cashRegister.merge(entry.getKey(), entry.getValue(), Integer::sum);
                }
                
                // Remove change from cash register
                for (Map.Entry<Currency, Integer> entry : changeToReturn.entrySet()) {
                    cashRegister.put(entry.getKey(), 
                        cashRegister.get(entry.getKey()) - entry.getValue());
                }
                
                // Decrement product quantity
                item.decrementQuantity();
                
                System.out.println("Transaction successful!");
                System.out.println("Dispensed: " + product.getName());
                if (changeNeeded.compareTo(BigDecimal.ZERO) > 0) {
                    System.out.println("Change returned: $" + changeNeeded);
                    displayChange(changeToReturn);
                }
                
                return new TransactionResult(product, changeToReturn, totalPaid);
                
            } finally {
                cashLock.unlock();
            }
        } finally {
            inventoryLock.unlock();
        }
    }
    
    private BigDecimal calculateTotalPayment(Map<Currency, Integer> payment) {
        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<Currency, Integer> entry : payment.entrySet()) {
            BigDecimal amount = entry.getKey().getValue()
                .multiply(new BigDecimal(entry.getValue()));
            total = total.add(amount);
        }
        return total.setScale(2, RoundingMode.HALF_UP);
    }
    
    private Map<Currency, Integer> calculateChange(BigDecimal changeNeeded) 
            throws InsufficientChangeException {
        Map<Currency, Integer> change = new HashMap<>();
        BigDecimal remaining = changeNeeded;
        
        // Sort currencies by value in descending order
        Currency[] currencies = Currency.values();
        Arrays.sort(currencies, (a, b) -> b.getValue().compareTo(a.getValue()));
        
        for (Currency currency : currencies) {
            int availableCount = cashRegister.getOrDefault(currency, 0);
            if (availableCount > 0 && remaining.compareTo(currency.getValue()) >= 0) {
                int neededCount = remaining.divide(currency.getValue(), 0, RoundingMode.DOWN).intValue();
                int actualCount = Math.min(neededCount, availableCount);
                
                if (actualCount > 0) {
                    change.put(currency, actualCount);
                    remaining = remaining.subtract(currency.getValue().multiply(new BigDecimal(actualCount)));
                }
            }
        }
        
        // Check if we can make exact change
        if (remaining.compareTo(BigDecimal.ZERO) > 0) {
            throw new InsufficientChangeException(
                "Cannot make exact change. Missing: $" + remaining);
        }
        
        return change;
    }
    
    private void displayChange(Map<Currency, Integer> change) {
        if (change.isEmpty()) return;
        
        System.out.println("Change breakdown:");
        for (Map.Entry<Currency, Integer> entry : change.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + entry.getValue());
        }
    }
    
    // Administrative functions
    public void addCash(Currency currency, int count) {
        cashLock.lock();
        try {
            cashRegister.merge(currency, count, Integer::sum);
            System.out.println("Added " + count + " " + currency + " to cash register");
        } finally {
            cashLock.unlock();
        }
    }
    
    public void collectCash() {
        cashLock.lock();
        try {
            BigDecimal totalCollected = BigDecimal.ZERO;
            System.out.println("\n=== Cash Collection ===");
            
            for (Map.Entry<Currency, Integer> entry : cashRegister.entrySet()) {
                if (entry.getValue() > 0) {
                    BigDecimal amount = entry.getKey().getValue()
                        .multiply(new BigDecimal(entry.getValue()));
                    totalCollected = totalCollected.add(amount);
                    System.out.println(entry.getKey() + ": " + entry.getValue() + 
                                     " ($" + amount + ")");
                }
            }
            
            System.out.println("Total collected: $" + totalCollected);
            System.out.println("=====================\n");
            
            // Reset cash register but keep minimal change
            initializeCashRegister();
        } finally {
            cashLock.unlock();
        }
    }
    
    public void displayCashRegister() {
        cashLock.lock();
        try {
            System.out.println("\n=== Cash Register Status ===");
            BigDecimal total = BigDecimal.ZERO;
            
            for (Map.Entry<Currency, Integer> entry : cashRegister.entrySet()) {
                BigDecimal amount = entry.getKey().getValue()
                    .multiply(new BigDecimal(entry.getValue()));
                total = total.add(amount);
                System.out.println(entry.getKey() + ": " + entry.getValue() + 
                                 " ($" + amount + ")");
            }
            
            System.out.println("Total in register: $" + total);
            System.out.println("===========================\n");
        } finally {
            cashLock.unlock();
        }
    }
    
    // Demo/Test method
    public static void main(String[] args) {
        VendingMachine machine = new VendingMachine();
        
        // Add some products
        machine.addProduct(new Product("A1", "Coke", new BigDecimal("1.50")), 10);
        machine.addProduct(new Product("A2", "Pepsi", new BigDecimal("1.50")), 8);
        machine.addProduct(new Product("B1", "Chips", new BigDecimal("2.00")), 5);
        machine.addProduct(new Product("B2", "Cookies", new BigDecimal("2.50")), 3);
        machine.addProduct(new Product("C1", "Water", new BigDecimal("1.00")), 15);
        
        // Display initial state
        machine.displayProducts();
        machine.displayCashRegister();
        
        try {
            // Test purchase with exact change
            Map<Currency, Integer> payment1 = new HashMap<>();
            payment1.put(Currency.ONE_DOLLAR, 1);
            payment1.put(Currency.QUARTER, 2);
            
            System.out.println("=== Test Purchase 1: Coke with $1.50 ===");
            machine.purchaseProduct("A1", payment1);
            
            // Test purchase requiring change
            Map<Currency, Integer> payment2 = new HashMap<>();
            payment2.put(Currency.FIVE_DOLLAR, 1);
            
            System.out.println("\n=== Test Purchase 2: Water with $5.00 ===");
            machine.purchaseProduct("C1", payment2);
            
            // Test insufficient funds
            Map<Currency, Integer> payment3 = new HashMap<>();
            payment3.put(Currency.QUARTER, 3);
            
            System.out.println("\n=== Test Purchase 3: Chips with $0.75 (Should fail) ===");
            machine.purchaseProduct("B1", payment3);
            
        } catch (Exception e) {
            System.out.println("Transaction failed: " + e.getMessage());
        }
        
        // Display final state
        System.out.println("\n=== Final State ===");
        machine.displayProducts();
        machine.displayCashRegister();
        
        // Demonstrate concurrent access safety
        demonstrateConcurrency(machine);
    }
    
    private static void demonstrateConcurrency(VendingMachine machine) {
        System.out.println("\n=== Concurrent Access Test ===");
        
        // Add more products for testing
        machine.addProduct(new Product("TEST", "Test Item", new BigDecimal("1.00")), 5);
        
        // Create multiple threads trying to buy the same product
        Thread[] threads = new Thread[3];
        for (int i = 0; i < threads.length; i++) {
            final int threadId = i;
            threads[i] = new Thread(() -> {
                try {
                    Map<Currency, Integer> payment = new HashMap<>();
                    payment.put(Currency.ONE_DOLLAR, 1);
                    
                    TransactionResult result = machine.purchaseProduct("TEST", payment);
                    System.out.println("Thread " + threadId + " successfully purchased: " + 
                                     result.getProduct().getName());
                } catch (Exception e) {
                    System.out.println("Thread " + threadId + " failed: " + e.getMessage());
                }
            });
        }
        
        // Start all threads
        for (Thread thread : threads) {
            thread.start();
        }
        
        // Wait for all threads to complete
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        machine.displayProducts();
    }
}