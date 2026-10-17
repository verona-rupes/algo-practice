# 05 — Intermediate Operations

All intermediate ops are **lazy** and return a new stream.

## filter

```java
stream.filter(x -> x > 0)
```

Keeps elements where the predicate is true. Prefer early filters (cheaper work on fewer elements).

## map vs flatMap — the interview essential

### map — 1 → 1

```java
Stream<String> words = Stream.of("hi", "world");
Stream<Integer> lengths = words.map(String::length); // 2, 5
```

### flatMap — 1 → 0..N (then flatten)

```java
Stream<List<String>> nested = Stream.of(
    List.of("a", "b"),
    List.of("c")
);
Stream<String> flat = nested.flatMap(List::stream); // a, b, c
```

Mental model:

```
map:      A → B
flatMap:  A → Stream<B>   then concatenate those streams
```

Classic use cases:
- Nested collections → single stream
- Optional → stream (`opt.stream()` in Java 9+)
- Expanding one entity into many (order → order lines)

```java
orders.stream()
    .flatMap(o -> o.lines().stream())
    .map(Line::amount)
    .sum();
```

`mapMulti` (Java 16+) is an imperative, often faster alternative to some `flatMap` patterns.

## distinct / sorted / limit / skip

```java
stream.distinct()                 // equals/hashCode based
stream.sorted()                   // natural order
stream.sorted(Comparator…)        // custom
stream.limit(10)                  // short-circuit friendly
stream.skip(5)
stream.takeWhile(p)               // Java 9+ prefix while true
stream.dropWhile(p)               // Java 9+ drop prefix while true
```

`sorted` is **stateful** — may buffer the whole stream. Costly on huge / infinite streams (infinite + sorted = disaster).

## peek — debugging only

```java
.stream()
.peek(x -> System.out.println("seen " + x))
.filter(...)
```

`peek` is for debugging/logging. **Do not** use it for business side effects — those belong in terminal ops (or better: don't mix side effects into pipelines).

## Stateful vs stateless

| Stateless | Stateful |
|-----------|----------|
| `filter`, `map`, `flatMap`, `peek` | `distinct`, `sorted`, `limit`, `skip` |

Stateful ops need memory / coordination — especially painful in parallel streams.

## Boxing bridges

```java
stream.mapToInt(String::length)   // Stream<T> → IntStream
intStream.mapToObj(i -> "n="+i)   // IntStream → Stream<T>
intStream.boxed()                 // IntStream → Stream<Integer>
```

## Operation ordering matters

```java
// BAD: sort everything, then filter
list.stream().sorted().filter(heavyPredicate).toList();

// BETTER: filter first, sort fewer elements
list.stream().filter(heavyPredicate).sorted().toList();
```

```java
// limit before expensive map when you only need N results
stream.map(expensive).limit(10);   // may still process more than 10 depending on ops
stream.limit(10).map(expensive);   // clearly only maps 10 (for ordered sequential)
```

## Checkpoint

- Explain `map` vs `flatMap` with nested lists
- Name two stateful intermediate ops and why they cost memory
- Know why `peek` isn't for production side effects

→ Next: [06 — Terminal Operations](06-terminal-operations.md)
