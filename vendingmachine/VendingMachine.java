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

    public VendingMachine(){
        this.inventory = new ConcurrentHashMap<>();
        this.cashRegister = new ConcurrentHashMap<>();
        this.inventoryLock = new ReentrantLock();
        this.cashLock = new ReentrantLock();

        initialiseCashRegister();
    }

    public void initialiseCashRegister(){
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

    public void addProduct(Product product, int quantity){
        inventoryLock.lock();
        try{
            InventoryItem item = inventory.get(product.getId());
            if(item!=null){
                item.setQuantity(item.getQuantity() + quantity);
            }else{
                inventory.put(product.getId(), new InventoryItem(product, quantity));
            }
            System.out.println("Added/Restocked: " + product.getName() + " (Quantity: " + 
                             inventory.get(product.getId()).getQuantity() + ")");
        }finally{
            inventoryLock.unlock();
        }
    }

    public void displayProducts(){
        inventoryLock.lock();
        try{
            for(InventoryItem item: inventory.values()){
                String status = item.isAvailable() ? "In stock (" + item.getQuantity() + ")" : "Out of stock!";
                System.out.println(item.getProduct() + " - " + status);
            }
        }finally{
            inventoryLock.unlock();
        }
    }

    public TransactionResult purchaseProduct(String productId, Map<Currency, Integer> payment) throws Exception{
        
        InventoryItem item = inventory.get(productId);
        if(item==null){
            throw new Exception("Product not found: " + productId);
        }

        Product product = item.getProduct();

        BigDecimal totalPaid = calculateTotalPayment(payment);

        if(totalPaid.compareTo(product.getPrice())<0){
            throw new Exception(String.format("Insufficient payment. Required $%.2f, Provided: $%.2f", product.getPrice(), totalPaid));
        }

        BigDecimal changeNeeded = totalPaid.subtract(product.getPrice());

        inventoryLock.lock();
        try{
            cashLock.lock();
            try{
                if(!item.isAvailable()){
                    throw new Exception("Product out of stock: " + product.getName());
                }

                Map<Currency, Integer> changeToReturn = new HashMap<>();
                if(changeNeeded.compareTo(BigDecimal.ZERO)>0){
                    changeToReturn = calculateChange(changeNeeded);
                }

                for(Map.Entry<Currency, Integer> entry : payment.entrySet()){
                    cashRegister.merge(entry.getKey(), entry.getValue(), Integer::sum);
                }

                for (Map.Entry<Currency, Integer> entry : changeToReturn.entrySet()) {
                    cashRegister.put(entry.getKey(), 
                        cashRegister.get(entry.getKey()) - entry.getValue());
                }

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

    private void displayChange(Map<Currency, Integer> change) {
        if (change.isEmpty()) return;
        
        System.out.println("Change breakdown:");
        for (Map.Entry<Currency, Integer> entry : change.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + entry.getValue());
        }
    }

    public BigDecimal calculateTotalPayment(Map<Currency, Integer> payment){
        BigDecimal total = BigDecimal.ZERO;
        for(Map.Entry<Currency, Integer> entry: payment.entrySet()){
            BigDecimal amount = entry.getKey().getValue().multiply(new BigDecimal(entry.getValue()));
            total=total.add(amount);
        }
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    public Map<Currency, Integer> calculateChange(BigDecimal changeNeeded) throws Exception{
        Map<Currency, Integer> change = new HashMap<>();
        BigDecimal remaining = changeNeeded;

        Currency[] currencies = Currency.values();
        Arrays.sort(currencies, (a,b) -> b.getValue().compareTo(a.getValue()));

        for(Currency currency : currencies){
            int availableCount = cashRegister.getOrDefault(currency, 0);
            if(availableCount > 0 && remaining.compareTo(currency.getValue()) >=0){
                int neededCount = remaining.divide(currency.getValue(), 0, RoundingMode.DOWN).intValue();
                int actualCount = Math.min(neededCount, availableCount);

                if (actualCount > 0) {
                    change.put(currency, actualCount);
                    remaining = remaining.subtract(currency.getValue().multiply(new BigDecimal(actualCount)));
                }
            }
        }

        if (remaining.compareTo(BigDecimal.ZERO) > 0) {
            throw new Exception("Cannot make exact change. Missing: $" + remaining);
        }
        return change;
    }

    public static void main(String[] args) {
        VendingMachine machine = new VendingMachine();
        
        machine.addProduct(new Product("A1", "Coke", new BigDecimal("1.50")), 10);
        machine.addProduct(new Product("A2", "Pepsi", new BigDecimal("1.50")), 8);
        machine.addProduct(new Product("B1", "Chips", new BigDecimal("2.00")), 5);
        machine.addProduct(new Product("B2", "Cookies", new BigDecimal("2.50")), 3);
        machine.addProduct(new Product("C1", "Water", new BigDecimal("1.00")), 15);

        machine.displayProducts();

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
    }

}
