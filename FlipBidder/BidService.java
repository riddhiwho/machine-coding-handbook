package FlipBidder;

public class BidService {
    public void createOrUpdateBid(Buyer buyer, Auction auction, Double amount) {
        if (auction.isClosed()) {
            System.out.println("Auction is closed. Cannot place or update bid.");
            return;
        }

        if (amount < auction.getLowestBidLimit() || amount > auction.getHighestBidLimit()) {
            System.out.println("Bid amount is out of auction limits.");
            return;
        }

        Bid existingBid = auction.getBids().get(buyer);

        if (existingBid == null) {
            buyer.incrementParticipation();
            auction.getBids().put(buyer, new Bid(buyer, amount));
        } else {
            existingBid.setAmount(amount);
            existingBid.withdraw(); // withdraw old bid
            auction.getBids().put(buyer, new Bid(buyer, amount)); // fresh bid
        }
    }

    public void withdrawBid(Buyer buyer, Auction auction) {
        if (auction.isClosed()) {
            System.out.println("Auction is closed. Cannot withdraw bid.");
            return;
        }

        Bid existingBid = auction.getBids().get(buyer);
        if (existingBid != null) {
            existingBid.withdraw();
        } else {
            System.out.println("No bid found to withdraw.");
        }
    }

}
