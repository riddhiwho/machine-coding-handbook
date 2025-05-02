package FlipBidder;

public class Bid {
    private Buyer buyer;
    private Double amount;
    private boolean isWithdrawn;

    public Bid(Buyer buyer, Double amount){
        this.buyer=buyer;
        this.amount=amount;
        this.isWithdrawn=false;
    }

    public Buyer getBuyer(){
        return buyer;
    }
    
    public Double getAmount(){
        return amount;
    }

    public boolean isWithdrawn(){
        return isWithdrawn;
    }

    public void setAmount(Double amount){
        this.amount=amount;
    }

    public void withdraw(){
        this.isWithdrawn = true;
    }

}
