package examples;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Module 02 — Lambdas & effectively final
 */
public class Lesson02_Lambdas {

    String name = "outer";

    public static void main(String[] args) throws Exception {
        // Forms
        Runnable noArgs = () -> System.out.println("no-args");
        Function<String, Integer> one = s -> s.length();
        Function<String, Integer> typed = (String s) -> s.length();
        Function<String, Integer> block = s -> {
            int n = s.length();
            return n * 2;
        };

        noArgs.run();
        System.out.println(one.apply("hi"));
        System.out.println(typed.apply("hi"));
        System.out.println(block.apply("hi")); // 4

        Callable<Integer> c = () -> 42;
        System.out.println(c.call());

        // Effectively final
        int factor = 3;
        Function<Integer, Integer> mul = x -> x * factor; // OK
        System.out.println(mul.apply(10)); // 30
        // factor = 4; // would break compilation of the lambda above

        // Capture workaround (prefer reduce in real code)
        int[] box = {0};
        List.of(1, 2, 3).forEach(x -> box[0] += x);
        System.out.println("boxed sum=" + box[0]);

        // Prefer:
        int sum = List.of(1, 2, 3).stream().mapToInt(Integer::intValue).sum();
        System.out.println("stream sum=" + sum);

        new Lesson02_Lambdas().showThis();

        // Shadowing — parameter cannot reuse local name
        String s = "outer-local";
        Predicate<String> p = str -> str.isEmpty() || s.startsWith("o");
        System.out.println(p.test(""));
    }

    void showThis() {
        Runnable lambda = () -> System.out.println("this.name=" + this.name); // outer
        lambda.run();

        Runnable anon = new Runnable() {
            String name = "anon";
            @Override public void run() {
                System.out.println("anon this.name=" + this.name);
            }
        };
        anon.run();
    }
}
