package FlipBidder;

import java.util.HashMap;
import java.util.Map;

public class AuctionService {

    private final Map<String, Auction> auctions = new HashMap<>();

    public void createAuction(String id, Double lowestLimit, Double highestLimit, Double participationCost, Seller seller) {
        if (!auctions.containsKey(id)) {
            auctions.put(id, new Auction(id, lowestLimit, highestLimit, participationCost, seller));
        }
    }

    public Auction getAuction(String id) {
        return auctions.get(id);
    }

    public Bid closeAuction(String id) {
        Auction auction = auctions.get(id);
        if (auction != null && !auction.isClosed()) {
            auction.closeAuction();
            return auction.getWinnerStrategy().determineWinner(auction);
        }
        return null;
    }
}
