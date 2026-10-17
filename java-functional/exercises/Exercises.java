package exercises;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Do these BEFORE opening solutions/Solutions.java
 *
 * Compile/run after filling TODOs:
 *   javac exercises/Exercises.java && java exercises.Exercises
 */
public class Exercises {

    record Product(String name, String category, double price, boolean inStock) {}
    record OrderLine(String sku, int qty, double unitPrice) {}
    record Order(String id, List<OrderLine> lines) {}

    public static void main(String[] args) {
        List<Product> catalog = List.of(
            new Product("Keyboard", "Electronics", 60, true),
            new Product("Mouse", "Electronics", 25, true),
            new Product("Desk", "Furniture", 200, false),
            new Product("Chair", "Furniture", 150, true),
            new Product("USB-C Cable", "Electronics", 12, true),
            new Product("Lamp", "Furniture", 40, true)
        );

        List<Order> orders = List.of(
            new Order("o1", List.of(
                new OrderLine("Keyboard", 1, 60),
                new OrderLine("Mouse", 2, 25))),
            new Order("o2", List.of(
                new OrderLine("Chair", 1, 150))),
            new Order("o3", List.of(
                new OrderLine("USB-C Cable", 5, 12),
                new OrderLine("Lamp", 1, 40)))
        );

        // E1: names of in-stock products under $50, uppercase, sorted
        List<String> e1 = null; // TODO
        System.out.println("E1=" + e1);

        // E2: Map category → count of products
        Map<String, Long> e2 = null; // TODO
        System.out.println("E2=" + e2);

        // E3: Map category → average price (only in-stock)
        Map<String, Double> e3 = null; // TODO
        System.out.println("E3=" + e3);

        // E4: partition products by inStock, then list names in each bucket
        Map<Boolean, List<String>> e4 = null; // TODO
        System.out.println("E4=" + e4);

        // E5: total revenue across all orders (qty * unitPrice, flatMap lines)
        double e5 = 0; // TODO
        System.out.println("E5=" + e5);

        // E6: word frequency for the sentence below (ignore case)
        String sentence = "To be or not to be";
        Map<String, Long> e6 = null; // TODO
        System.out.println("E6=" + e6);

        // E7: Optional chain — return domain of email if user present
        Optional<String> e7 = domainOf("ada"); // TODO implement domainOf
        System.out.println("E7=" + e7);

        // E8: top-priced product name per category
        Map<String, String> e8 = null; // TODO
        System.out.println("E8=" + e8);
    }

    /** Use findUser + findEmail with map/flatMap. */
    static Optional<String> domainOf(String userId) {
        // TODO
        return Optional.empty();
    }

    static Optional<String> findUser(String id) {
        return "ada".equals(id) ? Optional.of("Ada") : Optional.empty();
    }

    static Optional<String> findEmail(String user) {
        return Optional.of(user.toLowerCase() + "@contoso.com");
    }
}
