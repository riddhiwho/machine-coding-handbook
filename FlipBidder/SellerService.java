package FlipBidder;

import java.util.HashMap;
import java.util.Map;

public class SellerService {
    private final Map<String, Seller> sellers = new HashMap<>();

    public void addSeller(String name) {
        if (!sellers.containsKey(name)) {
            sellers.put(name, new Seller(name));
        }
    }

    public Seller getSeller(String name) {
        return sellers.get(name);
    }
}
