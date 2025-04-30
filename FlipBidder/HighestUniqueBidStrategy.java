package FlipBidder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HighestUniqueBidStrategy implements WinnerStrategy {
    
    @Override
    public Bid determineWinner(Auction auction) {
        Map<Double, List<Bid>> amountToBids = new HashMap<>();

        for (Bid bid : auction.getBids().values()) {
            amountToBids.computeIfAbsent(bid.getAmount(), k -> new ArrayList<>()).add(bid);
        }

        List<Double> uniqueAmounts = new ArrayList<>();
        for (Map.Entry<Double, List<Bid>> entry : amountToBids.entrySet()) {
            if (entry.getValue().size() == 1) {
                uniqueAmounts.add(entry.getKey());
            }
        }

        uniqueAmounts.sort(Collections.reverseOrder()); // Highest first

        for (Double amount : uniqueAmounts) {
            List<Bid> bids = amountToBids.get(amount);
            if (bids.size() == 1) {
                return bids.get(0);
            }
        }

        return null; // No unique highest bid
    }

}
