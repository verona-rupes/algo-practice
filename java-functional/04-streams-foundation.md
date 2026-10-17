# 04 — Streams Foundation

## What a Stream is (and is not)

A `Stream` is a **pipeline for processing a sequence of elements**.

| Streams ARE | Streams are NOT |
|-------------|-----------------|
| Lazy until a terminal op | A data structure / storage |
| Consumable once | Reusable like a `List` |
| Possibly infinite | Always finite |
| Possibly parallel | Always faster when parallel |

```java
List<String> names = List.of("Ann", "Bob", "Ada");

long count = names.stream()          // source
    .filter(n -> n.startsWith("A"))  // intermediate (lazy)
    .map(String::toUpperCase)        // intermediate (lazy)
    .count();                        // terminal (triggers work) → 2
```

## Pipeline anatomy

```
source → intermediate* → terminal
```

1. **Source** — creates the stream (`list.stream()`, `Stream.of(...)`, `Files.lines(...)`, …)
2. **Intermediate ops** — return a new `Stream`, do nothing yet (`filter`, `map`, `flatMap`, …)
3. **Terminal op** — produces a result or side effect; **starts** execution (`collect`, `reduce`, `forEach`, `count`, …)

After a terminal op, the stream is **consumed**. Reuse throws `IllegalStateException`.

## Laziness — the key insight

Intermediate ops build a recipe. Nothing runs until the terminal op.

```java
Stream<String> s = List.of("a", "bb", "ccc").stream()
    .filter(x -> {
        System.out.println("filter " + x);
        return x.length() > 1;
    })
    .map(x -> {
        System.out.println("map " + x);
        return x.toUpperCase();
    });

// Nothing printed yet!

List<String> result = s.collect(Collectors.toList());
// filter a
// filter bb
// map bb
// filter ccc
// map ccc
```

Elements flow **vertically** (one element through as much of the pipeline as needed), not "all filters then all maps". Short-circuiting terminals (`findFirst`, `anyMatch`) can stop early.

## Creating streams

```java
// Collections
list.stream();
list.parallelStream();
set.stream();
map.entrySet().stream();

// Explicit values
Stream.of("a", "b", "c");
Stream.ofNullable(maybeNull);          // empty if null (Java 9+)
Stream.empty();

// Generators / ranges
Stream.iterate(0, n -> n + 1);         // infinite — pair with limit!
Stream.iterate(0, n -> n < 10, n -> n + 1); // Java 9+ finite form
Stream.generate(Math::random);         // infinite
IntStream.range(0, 10);                // 0..9
IntStream.rangeClosed(0, 10);          // 0..10

// Arrays / strings / I/O
Arrays.stream(array);
"hello".chars();                       // IntStream of code points-ish (char values)
Files.lines(Path.of("file.txt"));      // use try-with-resources!
Pattern.compile(",").splitAsStream(csv);
Random.ints();
```

**Infinite streams without `limit`/`takeWhile`/`find*` = hang.**

## Primitive streams

`Stream<Integer>` boxes. Prefer:

- `IntStream`, `LongStream`, `DoubleStream`

```java
int sum = IntStream.rangeClosed(1, 100).sum();
OptionalDouble avg = employees.stream().mapToInt(Employee::salary).average();
IntStream.range(0, 5).mapToObj(i -> "row-" + i); // back to object stream
```

## Encounter order

- Ordered sources (`List`, `Arrays.stream` on array, `IntStream.range`) preserve order unless you say otherwise.
- `HashSet.stream()` — no encounter order guarantee.
- `unordered()` can unlock parallel speedups when order doesn't matter.

## Checkpoint

- Draw source / intermediate / terminal on any pipeline
- Explain laziness with a `println`-inside-filter example
- Create finite and infinite streams safely
- Prefer `IntStream` when summing numbers

→ Next: [05 — Intermediate Operations](05-intermediate-operations.md)
