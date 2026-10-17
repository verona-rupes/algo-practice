package solutions;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Reference solutions for exercises/Exercises.java
 */
public class Solutions {

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

        // E1
        List<String> e1 = catalog.stream()
            .filter(Product::inStock)
            .filter(p -> p.price() < 50)
            .map(Product::name)
            .map(String::toUpperCase)
            .sorted()
            .toList();
        System.out.println("E1=" + e1);
        // [LAMP, MOUSE, USB-C CABLE]

        // E2
        Map<String, Long> e2 = catalog.stream()
            .collect(Collectors.groupingBy(Product::category, Collectors.counting()));
        System.out.println("E2=" + e2);

        // E3
        Map<String, Double> e3 = catalog.stream()
            .filter(Product::inStock)
            .collect(Collectors.groupingBy(Product::category,
                Collectors.averagingDouble(Product::price)));
        System.out.println("E3=" + e3);

        // E4
        Map<Boolean, List<String>> e4 = catalog.stream()
            .collect(Collectors.partitioningBy(Product::inStock,
                Collectors.mapping(Product::name, Collectors.toList())));
        System.out.println("E4=" + e4);

        // E5 = 60 + 50 + 150 + 60 + 40 = 360
        double e5 = orders.stream()
            .flatMap(o -> o.lines().stream())
            .mapToDouble(line -> line.qty() * line.unitPrice())
            .sum();
        System.out.println("E5=" + e5);

        // E6
        String sentence = "To be or not to be";
        Map<String, Long> e6 = List.of(sentence.toLowerCase().split("\\s+")).stream()
            .collect(Collectors.groupingBy(w -> w, Collectors.counting()));
        System.out.println("E6=" + e6);

        // E7
        Optional<String> e7 = domainOf("ada");
        System.out.println("E7=" + e7); // Optional[contoso.com]

        // E8
        Map<String, String> e8 = catalog.stream()
            .collect(Collectors.groupingBy(Product::category,
                Collectors.collectingAndThen(
                    Collectors.maxBy(Comparator.comparingDouble(Product::price)),
                    opt -> opt.map(Product::name).orElse("n/a"))));
        System.out.println("E8=" + e8);
    }

    static Optional<String> domainOf(String userId) {
        return findUser(userId)
            .flatMap(Solutions::findEmail)
            .map(email -> email.substring(email.indexOf('@') + 1));
    }

    static Optional<String> findUser(String id) {
        return "ada".equals(id) ? Optional.of("Ada") : Optional.empty();
    }

    static Optional<String> findEmail(String user) {
        return Optional.of(user.toLowerCase() + "@contoso.com");
    }
}
