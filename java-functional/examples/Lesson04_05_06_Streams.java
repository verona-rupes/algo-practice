package examples;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Modules 04–06 — Stream foundation, intermediate & terminal ops
 */
public class Lesson04_05_06_Streams {

    public static void main(String[] args) {
        lazinessDemo();
        mapVsFlatMap();
        terminals();
        primitiveStreams();
    }

    static void lazinessDemo() {
        System.out.println("--- laziness ---");
        Stream<String> pipeline = Stream.of("a", "bb", "ccc")
            .filter(x -> {
                System.out.println("  filter " + x);
                return x.length() > 1;
            })
            .map(x -> {
                System.out.println("  map " + x);
                return x.toUpperCase();
            });

        System.out.println("built pipeline (nothing run yet)");
        System.out.println("result=" + pipeline.collect(Collectors.toList()));
    }

    static void mapVsFlatMap() {
        System.out.println("--- map vs flatMap ---");
        Stream<List<String>> nested = Stream.of(List.of("a", "b"), List.of("c"));

        // map → Stream<Stream<String>>
        List<Stream<String>> mapped = nested.map(List::stream).toList();
        System.out.println("map size=" + mapped.size()); // 2 streams

        List<String> flat = Stream.of(List.of("a", "b"), List.of("c"))
            .flatMap(List::stream)
            .toList();
        System.out.println("flatMap=" + flat); // [a, b, c]
    }

    static void terminals() {
        System.out.println("--- terminals ---");
        List<Integer> nums = List.of(1, 2, 3, 4, 5);

        System.out.println("anyMatch even? " + nums.stream().anyMatch(n -> n % 2 == 0));
        System.out.println("allMatch empty? " + Stream.<Integer>empty().allMatch(n -> n > 0)); // true
        System.out.println("anyMatch empty? " + Stream.<Integer>empty().anyMatch(n -> n > 0)); // false

        Optional<Integer> firstEven = nums.stream().filter(n -> n % 2 == 0).findFirst();
        System.out.println("findFirst even=" + firstEven.orElse(-1));

        int sum = nums.stream().reduce(0, Integer::sum);
        System.out.println("reduce sum=" + sum);

        // short-circuit with limit + peek
        System.out.println("peek/limit order:");
        IntStream.rangeClosed(1, 10)
            .peek(n -> System.out.println("  peek " + n))
            .filter(n -> n % 2 == 0)
            .limit(2)
            .forEach(n -> System.out.println("  result " + n));
    }

    static void primitiveStreams() {
        System.out.println("--- IntStream ---");
        System.out.println(IntStream.rangeClosed(1, 100).sum());
        System.out.println(IntStream.of(5, 2, 9).max().orElse(-1));
    }
}
