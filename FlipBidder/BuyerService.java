package FlipBidder;

import java.util.HashMap;
import java.util.Map;

public class BuyerService {
    private final Map<String, Buyer> buyers = new HashMap<>();

    public void addBuyer(String name) {
        if (!buyers.containsKey(name)) {
            buyers.put(name, new Buyer(name));
        }
    }

    public Buyer getBuyer(String name) {
        return buyers.get(name);
    }
}
