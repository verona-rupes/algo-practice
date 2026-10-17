package examples;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Module 03 — Four kinds of method references
 */
public class Lesson03_MethodReferences {

    record Person(String name, int age) {}

    public static void main(String[] args) {
        // 1) Static
        Function<String, Integer> parse = Integer::parseInt;
        System.out.println(parse.apply("42"));

        // 2) Bound instance
        String prefix = "Hi ";
        Function<String, String> greet = prefix::concat;
        System.out.println(greet.apply("Ada"));

        List<String> sink = new ArrayList<>();
        List.of("a", "b").forEach(sink::add); // bound to sink
        System.out.println(sink);

        // 3) Unbound instance — receiver is first arg
        Function<String, Integer> len = String::length;
        BiFunction<String, String, Boolean> starts = String::startsWith;
        System.out.println(len.apply("hello"));
        System.out.println(starts.apply("hello", "he"));

        // 4) Constructor
        Supplier<List<String>> lists = ArrayList::new;
        Function<String, Person> people = name -> new Person(name, 0);
        BiFunction<String, Integer, Person> people2 = Person::new;
        System.out.println(lists.get());
        System.out.println(people2.apply("Bob", 30));

        // Array constructor
        Function<Integer, String[]> arrays = String[]::new;
        System.out.println(arrays.apply(3).length);
    }
}
