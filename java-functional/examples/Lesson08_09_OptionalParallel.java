package examples;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Modules 08–09 — Optional & parallel stream pitfalls
 */
public class Lesson08_09_OptionalParallel {

    public static void main(String[] args) {
        optionalDemo();
        parallelSafety();
    }

    static void optionalDemo() {
        System.out.println("--- Optional ---");
        Optional<String> present = Optional.of("java");
        Optional<String> absent = Optional.ofNullable(null);

        System.out.println(present.map(String::toUpperCase).orElse("n/a"));
        System.out.println(absent.orElseGet(() -> "default"));

        // flatMap when mapper returns Optional
        Optional<String> domain = findUser("ada")
            .flatMap(Lesson08_09_OptionalParallel::findEmail)
            .map(email -> email.substring(email.indexOf('@') + 1));
        System.out.println("domain=" + domain.orElse("unknown"));

        // Optional → stream
        List<String> names = List.of("ada", "nobody", "bob").stream()
            .map(Lesson08_09_OptionalParallel::findUser)
            .flatMap(Optional::stream)
            .toList();
        System.out.println("found=" + names);
    }

    static Optional<String> findUser(String id) {
        return switch (id) {
            case "ada" -> Optional.of("Ada");
            case "bob" -> Optional.of("Bob");
            default -> Optional.empty();
        };
    }

    static Optional<String> findEmail(String user) {
        return Optional.of(user.toLowerCase() + "@example.com");
    }

    static void parallelSafety() {
        System.out.println("--- parallel ---");

        // BAD pattern (racy) — do not use in production
        List<Integer> racy = new ArrayList<>();
        try {
            IntStream.range(0, 10_000).parallel().forEach(racy::add);
            System.out.println("racy size (may be wrong/crash): " + racy.size());
        } catch (Exception e) {
            System.out.println("racy failed as expected sometimes: " + e);
        }

        // GOOD
        List<Integer> safe = IntStream.range(0, 10_000).parallel()
            .boxed()
            .collect(Collectors.toList());
        System.out.println("safe size=" + safe.size());

        // Associativity reminder
        int sequential = IntStream.rangeClosed(1, 20).reduce(0, (a, b) -> a - b);
        int parallel = IntStream.rangeClosed(1, 20).parallel().reduce(0, (a, b) -> a - b);
        System.out.println("subtract sequential=" + sequential + " parallel=" + parallel
            + " (may differ — non-associative)");
    }
}
