package FlipBidder;

import java.util.HashMap;
import java.util.Map;

public class Auction {
    private String id;
    private Double lowestBidLimit;
    private Double higestBidLimit;
    private Double participationCost;
    private Seller seller;
    private Map<Buyer, Bid> bids;
    private boolean isClosed;
    private WinnerStrategy winnerStrategy;


    public Auction(String id, Double lowestBidAmount, Double highestBidAmount, Double participationCost, Seller seller){
        this.id=id;
        this.lowestBidLimit=lowestBidAmount;
        this.higestBidLimit=highestBidAmount;
        this.participationCost=participationCost;
        this.seller=seller;
        this.isClosed = false;
        this.bids = new HashMap<>();
        this.winnerStrategy = new HighestUniqueBidStrategy();
    }

    public String getId(){
        return id;
    }

    public Double getLowestBidLimit(){
        return lowestBidLimit;
    }

    public Double getHighestBidLimit(){
        return higestBidLimit;
    }

    public Double getParticipationCost(){
        return participationCost;
    }

    public Seller getSeller(){
        return seller;
    }

    public boolean isClosed(){
        return isClosed;
    }

    public void closeAuction(){
        this.isClosed=true;
    }
    public Map<Buyer, Bid> getBids(){
        return bids;
    }

    public WinnerStrategy getWinnerStrategy(){
        return winnerStrategy;
    }

    public void setWinnerStrategy(WinnerStrategy winnerStrategy){
        this.winnerStrategy=winnerStrategy;
    }

}
