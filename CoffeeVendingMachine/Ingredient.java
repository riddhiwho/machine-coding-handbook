package CoffeeVendingMachine;

public class Ingredient {
    private String name;
    private Integer quantity;

    public Ingredient(String name, Integer quantity){
        this.name=name;
        this.quantity=quantity;
    }

    public String getName(){
        return name;
    }

    public Integer getQuantity(){
        return quantity;
    }

    public void setQuantity(Integer updated){
        quantity = updated;
    }
}
