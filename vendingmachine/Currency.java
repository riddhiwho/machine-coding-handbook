import java.math.BigDecimal;

public enum Currency {
    
    PENNY(new BigDecimal("0.01")),
    NICKEL(new BigDecimal("0.05")),
    DIME(new BigDecimal("0.10")),
    QUARTER(new BigDecimal("0.25")),
    DOLLAR_COIN(new BigDecimal("1.00")),
    ONE_DOLLAR(new BigDecimal("1.00")),
    FIVE_DOLLAR(new BigDecimal("5.00")),
    TEN_DOLLAR(new BigDecimal("10.00")),
    TWENTY_DOLLAR(new BigDecimal("20.00"));

    private final BigDecimal value;

    Currency(BigDecimal value){
        this.value=value;
    }

    public BigDecimal getValue(){
        return value;
    }

}
