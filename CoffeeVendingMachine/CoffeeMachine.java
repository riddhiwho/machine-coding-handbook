package CoffeeVendingMachine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CoffeeMachine {
    List<Coffee> coffeeMenu;
    List<Ingredient> ingredients;

    public CoffeeMachine(){
        coffeeMenu = new ArrayList<>();
        ingredients = new ArrayList<>();
        initialiseMenu();
        initialiseIngredients();
    }

    public void initialiseMenu(){
        Coffee cappuccino = new Coffee("Cappuccino", 5.5, Map.of("Espresso", 1,
        "Steamed Milk", 1,
        "Milk Foam", 1));

        Coffee latte = new Coffee("Latte", 5.5, Map.of("Espresso", 1,
        "Steamed Milk", 3,
        "Milk Foam", 1));

        Coffee espresso = new Coffee("Espresso", 5.5, Map.of("Espresso", 1));

        Collections.addAll(coffeeMenu, cappuccino, latte, espresso);
    }

    public void initialiseIngredients(){
        Ingredient milkFoam = new Ingredient("Milk Foam", 5);
        Ingredient steamedMilk = new Ingredient("Steamed Milk", 5);
        Ingredient espresso = new Ingredient("Espresso", 5);

        Collections.addAll(ingredients, milkFoam, steamedMilk, espresso);
    }

    public void displayMenu(){
        for(Coffee coffee : coffeeMenu){
            System.out.print(coffee.getName() + " - $" + coffee.getPrice());
            System.out.println();
        }
    }

    public void selectCoffee(String input) {
        for (Coffee coffee : coffeeMenu) {
            if (coffee.getName().equalsIgnoreCase(input)) {
                Map<String, Integer> recipe = coffee.getRecipe();
    
                for (Map.Entry<String, Integer> entry : recipe.entrySet()) {
                    String ingredientName = entry.getKey();
                    int requiredQuantity = entry.getValue();
    
                    Ingredient matched = null;
                    for (Ingredient ing : ingredients) {
                        if (ing.getName().equalsIgnoreCase(ingredientName)) {
                            matched = ing;
                            break;
                        }
                    }
    
                    if (matched == null) {
                        System.out.println("Ingredient " + ingredientName + " is missing.");
                        return;
                    }
    
                    if (matched.getQuantity() < requiredQuantity) {
                        System.out.println("Not enough " + ingredientName + ". Available: "
                                + matched.getQuantity() + ", Required: " + requiredQuantity);
                        return;
                    }
                }
    
                // If we reach here, all ingredients are available
                // Deduct quantities
                for (Map.Entry<String, Integer> entry : recipe.entrySet()) {
                    String ingredientName = entry.getKey();
                    int requiredQuantity = entry.getValue();
    
                    for (Ingredient ing : ingredients) {
                        if (ing.getName().equalsIgnoreCase(ingredientName)) {
                            ing.setQuantity(ing.getQuantity() - requiredQuantity);
                            if(ing.getQuantity()<=2){
                                System.out.printf("Ingredient %s running low. Current quantity: %d%n", ing.getName(), ing.getQuantity());
                            }
                            break;
                        }
                    }
                }
    
                System.out.println("Serving " + coffee.getName());
                return;
            }
        }
    
        System.out.println("Coffee not available");
    }
    

    public void makePayment(Coffee selectedCoffee, Double input){  
        for(Coffee coffee : coffeeMenu){
            if(coffee.getName().equalsIgnoreCase(selectedCoffee.getName())){

                if(input<coffee.getPrice()){
                    System.out.printf("Insufficient amount. %s costs %.2f, but you entered %.2f", 
                    coffee.getName(), coffee.getPrice(), input);
                    return;
                }

                Double change = input - coffee.getPrice();

                if (change > 0) {
                    System.out.printf("Payment successful. Returning change: %.2f%n", change);
                } else {
                    System.out.println("Payment successful. Thank you!");
                }
                selectCoffee(selectedCoffee.getName());
                return;
            }
        }
    }
}
