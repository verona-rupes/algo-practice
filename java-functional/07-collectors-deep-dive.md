# 07 — Collectors Deep Dive

`collect(Collector)` is how streams build **mutable results** (lists, maps, strings, summaries).

```java
Collector<T, A, R>
// T = element type
// A = mutable accumulator type
// R = final result type
```

You rarely implement that by hand — use `Collectors.*` factory methods.

## Core collectors (know cold)

```java
toList() / toSet() / toCollection(TreeSet::new)
toMap(keyMapper, valueMapper)
toMap(keyMapper, valueMapper, mergeFunction)           // duplicate keys!
toMap(keyMapper, valueMapper, mergeFunction, TreeMap::new)

joining() / joining(", ") / joining(", ", "[", "]")

counting()
summingInt / averagingInt / summarizingInt              // IntSummaryStatistics
reducing(...)
mapping(mapper, downstream)                             // pre-map before downstream
filtering(predicate, downstream)                        // Java 9+
flatMapping(mapper, downstream)                         // Java 9+
```

### toMap duplicate-key trap

```java
// Throws IllegalStateException on duplicate keys
Collectors.toMap(Person::name, Person::age);

// Merge: keep existing, keep new, or combine
Collectors.toMap(Person::name, Person::age, (a, b) -> a);
Collectors.toMap(Person::name, Person::age, Integer::sum);
```

## groupingBy — the power tool

```java
Map<Department, List<Employee>> byDept =
    employees.stream()
        .collect(Collectors.groupingBy(Employee::dept));
```

### Downstream collectors (this is the expert move)

```java
// Count per dept
Map<Department, Long> counts =
    employees.stream()
        .collect(groupingBy(Employee::dept, counting()));

// Average salary per dept
Map<Department, Double> avgPay =
    employees.stream()
        .collect(groupingBy(Employee::dept, averagingInt(Employee::salary)));

// Names per dept (not full Employee objects)
Map<Department, List<String>> names =
    employees.stream()
        .collect(groupingBy(Employee::dept,
            mapping(Employee::name, toList())));

// Multi-level grouping
Map<Department, Map<Level, List<Employee>>> nested =
    employees.stream()
        .collect(groupingBy(Employee::dept, groupingBy(Employee::level)));
```

`groupingBy` is sequential-friendly; `groupingByConcurrent` for parallel.

## partitioningBy — special case of grouping into two buckets

```java
Map<Boolean, List<Employee>> parts =
    employees.stream()
        .collect(Collectors.partitioningBy(e -> e.salary() > 100_000));

List<Employee> rich = parts.get(true);
List<Employee> rest = parts.get(false);
```

Always produces **both** `true` and `false` keys (lists may be empty).

## teeing (Java 12+) — two collectors at once

```java
record Stats(long count, double avg) {}

Stats stats = employees.stream().collect(
    Collectors.teeing(
        Collectors.counting(),
        Collectors.averagingInt(Employee::salary),
        Stats::new
    )
);
```

## collectingAndThen — post-process the result

```java
List<String> immutable = stream.collect(
    collectingAndThen(toList(), List::copyOf)
);

Integer size = stream.collect(
    collectingAndThen(toSet(), Set::size)
);
```

## Custom collector sketch (expert level)

Use `Collector.of(supplier, accumulator, combiner, finisher, characteristics…)`:

```java
Collector<String, StringBuilder, String> joinPlus =
    Collector.of(
        StringBuilder::new,          // supplier
        StringBuilder::append,       // accumulator
        (a, b) -> { a.append(b); return a; }, // combiner (parallel)
        StringBuilder::toString      // finisher
    );
```

Characteristics matter: `UNORDERED`, `CONCURRENT`, `IDENTITY_FINISH`.

## Cheat sheet: "I want X"

| I want… | Use |
|---------|-----|
| `List` | `toList()` / `.toList()` |
| `Map<K,List<V>>` | `groupingBy` |
| `Map<K,V>` | `toMap` (+ merge) |
| `Map<Boolean,List<V>>` | `partitioningBy` |
| CSV string | `joining(",")` |
| Count/sum/avg per group | `groupingBy(..., counting/summing/averaging)` |
| Two aggregates | `teeing` |

## Checkpoint

- Write `groupingBy` + downstream `mapping` from memory
- Handle duplicate keys in `toMap`
- Explain `partitioningBy` vs `groupingBy`
- Sketch `Collector.of(...)` roles: supplier / accumulator / combiner / finisher

→ Next: [08 — Optional](08-optional.md)
