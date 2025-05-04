package CoffeeVendingMachine;

import java.util.Map;

public class Coffee {
    private String name;
    private Double price;
    private Map<String, Integer> recipe;


    public Coffee(String name, Double price, Map<String, Integer> recipe){
        this.name=name;
        this.price=price;
        this.recipe=recipe;
    }

    public String getName(){
        return name;
    }

    public Double getPrice(){
        return price;
    }

    public Map<String, Integer> getRecipe(){
        return recipe;
    }

}
