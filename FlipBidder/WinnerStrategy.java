package FlipBidder;

public interface WinnerStrategy {
    Bid determineWinner(Auction auction);
}
