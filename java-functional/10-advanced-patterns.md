# 10 — Advanced Patterns

## Function composition for pipelines of business rules

```java
Function<Order, Order> validate = this::validate;
Function<Order, Order> enrich   = this::enrich;
Function<Order, Order> price    = this::price;

Function<Order, Order> process = validate.andThen(enrich).andThen(price);
Order result = process.apply(order);
```

Same idea with `Predicate.and/or/negate` for rule engines.

## Checked exceptions in lambdas

Wrap once, reuse everywhere:

```java
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

list.stream()
    .map(ThrowingFunction.unchecked(path -> Files.readString(Path.of(path))))
    .toList();
```

## Lazy suppliers

```java
Supplier<Expensive> lazy = () -> new Expensive();
// or
Supplier<Expensive> memo = memoize(() -> new Expensive());
```

Useful for optional expensive defaults (`orElseGet`) and deferred config.

## Stream vs Optional fluency

```java
Optional<User> admin = users.stream()
    .filter(User::isAdmin)
    .findFirst();

String label = users.stream()
    .filter(User::isAdmin)
    .map(User::name)
    .findFirst()
    .orElse("none");
```

Don't force everything into streams — sometimes `Optional` chaining on a single value is clearer.

## Index-aware processing (without abusing streams)

```java
IntStream.range(0, list.size())
    .mapToObj(i -> new Indexed<>(i, list.get(i)))
    .filter(ix -> ix.value().matches(pred))
    .toList();
```

If you heavily need indices, a for-loop is often clearer.

## Custom Spliterator / StreamSupport (awareness level)

For advanced library code: turn a pull-based source into a stream via `StreamSupport.stream(spliterator, parallel)`.

Interview level: know it exists; implementing one is rare outside libraries.

## Collector characteristics & parallel correctness

When writing custom collectors, wrong characteristics → subtle bugs under `parallel()`. Prefer `Collectors` factories until you must go custom.

## Readability patterns experts use

```java
// Extract named predicates / functions
Predicate<Employee> senior = e -> e.years() >= 5;
Predicate<Employee> wellPaid = e -> e.salary() > 120_000;

employees.stream()
    .filter(senior.and(wellPaid))
    .map(Employee::name)
    .sorted()
    .toList();
```

Named pieces > giant inline lambdas.

## Performance notes worth knowing

1. **Autoboxing** kills hot streams → prefer `IntStream` etc.
2. **`sorted` + `limit(k)`** can be optimized by the JDK in some cases, but don't rely blindly — for top-K, a heap may be better.
3. **Don't stream just to stream** — `list.stream().toList()` is noise.
4. **Reuse collectors** — store `Collector` instances in `static final` fields when used heavily.
5. **`toList()` (Java 16+)** returns unmodifiable list; mutate → copy first.

## Decision framework

```
Need aggregate / transform over a collection?
  ├─ Simple filter/map/collect → Stream
  ├─ Grouping / partitioning → Collectors
  ├─ Complex mutable state / indices → for-loop
  ├─ Possibly absent single value → Optional
  └─ Measured CPU-bound bulk work → consider parallel
```

## Checkpoint

- Compose Functions/Predicates cleanly
- Wrap checked exceptions for stream use
- Choose stream vs loop vs Optional deliberately
- Avoid boxing in numeric pipelines

→ Next: [11 — Hard Interview Q&A](11-hard-interview-qa.md)
