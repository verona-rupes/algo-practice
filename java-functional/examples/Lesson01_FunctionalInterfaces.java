package examples;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

/**
 * Module 01 — Functional Interfaces
 * Run: javac examples/*.java && java examples.Lesson01_FunctionalInterfaces
 */
public class Lesson01_FunctionalInterfaces {

    @FunctionalInterface
    interface TriFunction<A, B, C, R> {
        R apply(A a, B b, C c);
    }

    public static void main(String[] args) {
        // Big Four
        Predicate<String> notBlank = s -> s != null && !s.isBlank();
        Function<String, Integer> length = String::length;
        Consumer<String> printer = s -> System.out.println("consume: " + s);
        Supplier<List<String>> newList = ArrayList::new;

        System.out.println(notBlank.test("hi"));       // true
        System.out.println(length.apply("hello"));     // 5
        printer.accept("world");
        System.out.println(newList.get().getClass().getSimpleName());

        // Composition
        Predicate<String> longEnough = s -> s.length() >= 3;
        Predicate<String> both = notBlank.and(longEnough);
        System.out.println(both.test("ab"));           // false
        System.out.println(both.test("abc"));          // true

        Function<String, String> trim = String::trim;
        Function<String, Integer> trimThenLen = trim.andThen(String::length);
        System.out.println(trimThenLen.apply("  hey  ")); // 3

        // Operators
        UnaryOperator<Integer> square = x -> x * x;
        BinaryOperator<Integer> sum = Integer::sum;
        System.out.println(square.apply(4));           // 16
        System.out.println(sum.apply(2, 3));           // 5

        // Custom FI
        TriFunction<Integer, Integer, Integer, Integer> sum3 = (x, y, z) -> x + y + z;
        System.out.println(sum3.apply(1, 2, 3));       // 6
    }
}
