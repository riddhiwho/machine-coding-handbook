package FlipBidder;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Auction {
    private String id;
    private Double lowestBidLimit;
    private Double higestBidLimit;
    private Double participationCost;
    private Seller seller;
    private Map<Buyer, Bid> bids;
    private boolean isClosed;
    private final WinnerStrategy winnerStrategy;

    public Auction(String id, Double lowestBidAmount, Double highestBidAmount, Double participationCost, Seller seller) {
        this.id = id;
        this.lowestBidLimit = lowestBidAmount;
        this.higestBidLimit = highestBidAmount;
        this.participationCost = participationCost;
        this.seller = seller;
        this.bids = new HashMap<>();
        this.isClosed = false;
        this.winnerStrategy = new HighestUniqueBidStrategy();
    }

    public void closeAuction() {
        this.isClosed = true;
    }
}
