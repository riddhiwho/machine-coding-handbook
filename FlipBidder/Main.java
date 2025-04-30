package FlipBidder;

public class Main {
    public static void main(String[] args) {
        BuyerService buyerService = new BuyerService();
        SellerService sellerService = new SellerService();
        AuctionService auctionService = new AuctionService();
        BidService bidService = new BidService();

        buyerService.addBuyer("buyer1");
        buyerService.addBuyer("buyer2");
        buyerService.addBuyer("buyer3");

        sellerService.addSeller("seller1");

        auctionService.createAuction("A1", 10.0, 50.0, 1.0, sellerService.getSeller("seller1"));

        bidService.createOrUpdateBid(buyerService.getBuyer("buyer1"), auctionService.getAuction("A1"), 17.0);
        bidService.createOrUpdateBid(buyerService.getBuyer("buyer2"), auctionService.getAuction("A1"), 15.0);
        bidService.createOrUpdateBid(buyerService.getBuyer("buyer2"), auctionService.getAuction("A1"), 19.0);
        bidService.createOrUpdateBid(buyerService.getBuyer("buyer3"), auctionService.getAuction("A1"), 19.0);

        Bid winningBid = auctionService.closeAuction("A1");

        if (winningBid != null) {
            System.out.println("Winner for A1: " + winningBid.getBuyer().getName() + " with bid: " + winningBid.getAmount());
        } else {
            System.out.println("No winner for A1");
        }



        sellerService.addSeller("seller2");
        auctionService.createAuction("A2", 5.0, 20.0, 2.0, sellerService.getSeller("seller2"));

        bidService.createOrUpdateBid(buyerService.getBuyer("buyer3"), auctionService.getAuction("A2"), 25.0); // Should fail
        bidService.createOrUpdateBid(buyerService.getBuyer("buyer2"), auctionService.getAuction("A2"), 5.0);
        bidService.withdrawBid(buyerService.getBuyer("buyer2"), auctionService.getAuction("A2"));

        winningBid = auctionService.closeAuction("A2");

        if (winningBid != null) {
            System.out.println("Winner for A2: " + winningBid.getBuyer().getName() + " with bid: " + winningBid.getAmount());
        } else {
            System.out.println("No winner for A2");
        }


    }
}
