import java.math.BigDecimal;
import java.util.Map;

class TransactionResult {
    private final Product product;
    private final Map<Currency, Integer> change;
    private final BigDecimal totalPaid;
    
    public TransactionResult(Product product, Map<Currency, Integer> change, BigDecimal totalPaid) {
        this.product = product;
        this.change = change;
        this.totalPaid = totalPaid;
    }
    
    public Product getProduct() { return product; }
    public Map<Currency, Integer> getChange() { return change; }
    public BigDecimal getTotalPaid() { return totalPaid; }
}