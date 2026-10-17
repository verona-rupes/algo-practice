# 06 — Terminal Operations

A terminal op **triggers** the pipeline and produces a result or side effect. Only one per stream.

## Categories

| Category | Ops | Returns |
|----------|-----|---------|
| Aggregate | `count`, `min`, `max`, `sum`, `average` | number / Optional |
| Match (short-circuit) | `anyMatch`, `allMatch`, `noneMatch` | boolean |
| Find (short-circuit) | `findFirst`, `findAny` | Optional |
| Reduce | `reduce` | Optional / U |
| Collect | `collect` | mutable result container |
| Side-effect | `forEach`, `forEachOrdered` | void |
| Materialize | `toArray`, `toList` (Java 16+) | array / List |

## Short-circuiting

```java
boolean hasAdmin = users.stream().anyMatch(User::isAdmin); // stops at first hit
Optional<User> first = users.stream().filter(User::isActive).findFirst();
```

`allMatch` on empty stream → `true` (vacuous truth). Same for `noneMatch` → `true`. Interview favorite!

## reduce — three forms

```java
// 1) identity + accumulator
int sum = Stream.of(1, 2, 3).reduce(0, Integer::sum); // 6

// 2) no identity → Optional
Optional<Integer> max = Stream.of(1, 2, 3).reduce(Integer::max);

// 3) identity + accumulator + combiner (parallel-ready)
int sum2 = Stream.of("a", "bb", "ccc")
    .reduce(0, (acc, s) -> acc + s.length(), Integer::sum);
```

**Identity** must be a true identity for the op (`0` for sum, `1` for product, `""` for concat — careful: concat identity works but is O(n²) with strings; prefer `Collectors.joining`).

When to prefer `collect` over `reduce`:
- Building mutable containers (`List`, `Map`, `StringBuilder`)
- Parallel mutable aggregation (collectors are designed for this)

## forEach vs forEachOrdered

```java
parallelStream.forEach(System.out::println);        // order NOT guaranteed
parallelStream.forEachOrdered(System.out::println); // encounters order, less parallel benefit
```

Prefer collecting results over `forEach` mutations.

## collect (preview — deep dive in 07)

```java
List<String> list = stream.toList();                 // unmodifiable (Java 16+)
List<String> mut  = stream.collect(Collectors.toList());
Set<String> set   = stream.collect(Collectors.toSet());
String joined     = stream.collect(Collectors.joining(", "));
```

## When a classic for-loop wins

Be ready to say this in interviews:

1. **Early exit with complex state** — nested breaks, labeled continues
2. **Index-heavy algorithms** — sliding windows with `i`, `j`
3. **Checked exceptions** everywhere — streams get noisy
4. **Hot tight loops** — allocation / boxing overhead matters
5. **Readability** — a 3-line loop beats a clever 1-line stream

Streams excel at: declare *what*, filter/map/group/aggregate over collections, parallelizing embarrassingly parallel work.

## Checkpoint

- List short-circuiting terminal ops
- Write all three `reduce` forms
- Explain empty-stream `allMatch` / `noneMatch`
- Name 3 cases to prefer a for-loop

→ Next: [07 — Collectors Deep Dive](07-collectors-deep-dive.md)
