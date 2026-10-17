package examples;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Module 10 — composition, checked exceptions, readability
 */
public class Lesson10_Advanced {

    @FunctionalInterface
    interface ThrowingFunction<T, R> {
        R apply(T t) throws Exception;

        static <T, R> Function<T, R> unchecked(ThrowingFunction<T, R> f) {
            return t -> {
                try {
                    return f.apply(t);
                } catch (RuntimeException e) {
                    throw e;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            };
        }
    }

    public static void main(String[] args) {
        Predicate<String> notBlank = s -> s != null && !s.isBlank();
        Predicate<String> shortWord = s -> s.length() <= 5;
        Predicate<String> interesting = notBlank.and(shortWord);

        Function<String, String> normalize = ((Function<String, String>) String::trim)
            .andThen(String::toLowerCase);

        List<String> words = List.of("  Java ", "Streams", "API", "", "Lambda");
        List<String> result = words.stream()
            .map(normalize)
            .filter(interesting)
            .sorted()
            .toList();
        System.out.println(result);

        // Checked exception wrapping demo (in-memory, no real IO required)
        Function<String, String> identityRead = ThrowingFunction.unchecked(s -> {
            if (s.equals("boom")) {
                throw new IOException("simulated");
            }
            return s;
        });
        System.out.println(identityRead.apply("ok"));
        try {
            identityRead.apply("boom");
        } catch (RuntimeException e) {
            System.out.println("wrapped: " + e.getCause().getClass().getSimpleName());
        }

        // UncheckedIOException variant often used with Files
        Function<Path, String> read = path -> {
            try {
                return Files.readString(path);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        };
        System.out.println("read fn ready: " + (read != null));
    }
}
