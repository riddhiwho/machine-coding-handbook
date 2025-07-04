class InventoryItem {
    private Product product;
    private int quantity;
    
    public InventoryItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
    
    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    
    public boolean isInStock() { return quantity > 0; }
    public void decrementQuantity() { 
        if (quantity > 0) quantity--; 
    }
}