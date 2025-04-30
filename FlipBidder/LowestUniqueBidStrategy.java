package FlipBidder;

import java.util.*;
import java.util.List;

public class LowestUniqueBidStrategy implements WinnerStrategy {

    @Override
    public Bid determineWinner(Auction auction) {
        Map<Double, List<Bid>> amountToBids = new HashMap<>();

        for (Bid bid : auction.getBids().values()) {
            if (!bid.isWithdrawn()) {
                amountToBids.computeIfAbsent(bid.getAmount(), k -> new ArrayList<>()).add(bid);
            }
        }

        List<Double> uniqueAmounts = new ArrayList<>();
        for (Map.Entry<Double, List<Bid>> entry : amountToBids.entrySet()) {
            if (entry.getValue().size() == 1) {
                uniqueAmounts.add(entry.getKey());
            }
        }

        Collections.sort(uniqueAmounts); // Lowest first

        for (Double amount : uniqueAmounts) {
            List<Bid> bids = amountToBids.get(amount);
            if (bids.size() == 1) {
                return bids.get(0);
            }
        }

        return null; // No unique lowest bid
    }
}
