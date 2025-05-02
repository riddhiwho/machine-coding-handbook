package LRUCache;

public class LRUCacheDemo {
    public static void main(String[] args) {
        LRUCache<Integer, String> cache = new LRUCache<>(3);

        cache.put(1, "Harry");
        cache.put(2, "Hermione");
        cache.put(3, "Ron");

        System.out.println(cache.get(1));
        System.out.println(cache.get(2));

        cache.put(4, "Draco");

        System.out.println(cache.get(3));
        cache.get(4);

        cache.put(4, "Albus");

        System.out.println(cache.get(4));
        cache.get(2);
    }
}
