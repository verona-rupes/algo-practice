# One-page Cheat Sheet

## Functional interfaces

| FI | Method | Shape |
|----|--------|-------|
| `Predicate<T>` | `test` | T → boolean |
| `Function<T,R>` | `apply` | T → R |
| `Consumer<T>` | `accept` | T → void |
| `Supplier<T>` | `get` | () → T |
| `UnaryOperator<T>` | `apply` | T → T |
| `BinaryOperator<T>` | `apply` | (T,T) → T |

Compose: `and` / `or` / `negate` · `andThen` / `compose`

## Method references

| Kind | Example |
|------|---------|
| Static | `Integer::parseInt` |
| Bound | `System.out::println` |
| Unbound | `String::length` |
| Constructor | `ArrayList::new` |

## Stream skeleton

```java
source.stream()
  .filter(pred)          // intermediate, lazy
  .map(fn)               // 1→1
  .flatMap(fnToStream)   // 1→0..N flatten
  .distinct().sorted().limit(n)
  .collect(collector);   // terminal — triggers work
```

## Collectors

```java
toList() / toSet() / joining(",")
toMap(k, v) / toMap(k, v, merge)
groupingBy(classifier)
groupingBy(classifier, downstream)
partitioningBy(predicate)
mapping(fn, downstream)
counting() / summingInt / averagingInt
teeing(c1, c2, merger)
collectingAndThen(c, finisher)
```

## Optional

```java
of / ofNullable / empty
map / flatMap / filter
orElse(x)          // eager
orElseGet(sup)     // lazy
orElseThrow()
ifPresent / ifPresentOrElse
opt.stream()       // Java 9+
```

## Gotchas

- Effectively final captures only
- Stream = one-shot
- Empty `allMatch`/`noneMatch` → true; `anyMatch` → false
- `toMap` duplicates need merge fn
- Parallel + shared mutable = race
- Optional = return type, not field/param
- Filter early; prefer `IntStream` for numbers
