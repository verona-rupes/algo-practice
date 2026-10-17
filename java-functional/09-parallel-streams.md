# 09 — Parallel Streams

```java
list.parallelStream()...
stream.parallel()...
stream.sequential()...
```

Parallel streams split work across the **common `ForkJoinPool`** (by default).

## When parallel can help

All of these should be true-ish:

1. **Large** data set (thousands+; measure!)
2. **CPU-bound** per-element work (not tiny ops)
3. **Stateless**, side-effect-free ops
4. **Associative** accumulators / good splitability (`ArrayList`, arrays split well; `LinkedList` does not)
5. You are OK with non-deterministic `findAny` / unordered results

## When parallel hurts or breaks

| Problem | Why |
|---------|-----|
| Tiny lists | Thread overhead > work |
| Boxing-heavy pipelines | Allocation dominates |
| `LinkedList` / poor spliterators | Can't split efficiently |
| Shared mutable state | Race conditions |
| Non-associative reduce | Wrong answers |
| Blocking I/O in pipeline | Saturates FJP, starves other tasks |
| Order-sensitive `forEach` | Use `forEachOrdered` or don't parallelize |

### Broken reduce example

```java
// NON-ASSOCIATIVE — parallel may give wrong result
stream.parallel().reduce(0, (a, b) -> a - b);
```

Associative: `(a op b) op c == a op (b op c)`. Sum/product/min/max are; subtraction/concatenation-as-reduce need care.

## Thread-safety checklist

```java
// BAD — race on shared mutable list
List<String> out = new ArrayList<>();
stream.parallel().forEach(out::add);

// GOOD — collect
List<String> out = stream.parallel().toList();
```

Use `groupingByConcurrent` / `toConcurrentMap` when parallel grouping.

## Common pool caveats

- Default parallelism ≈ `Runtime.getRuntime().availableProcessors() - 0/1`
- A slow parallel task can stall **other** parallel streams / FJP users in the same JVM
- Custom pool (advanced): submit work wrapping `stream.parallel()` inside a custom `ForkJoinPool` (behavior varied by JDK version — know that it's tricky)

## Measurement mantra

> "Parallel without a benchmark is a guess."

Use JMH or at least rough timings on realistic data sizes.

## Checkpoint

- List 4 conditions for considering parallel
- Explain why mutating an external `ArrayList` in `forEach` is wrong
- Define associativity and why reduce needs it
- Default: stay sequential unless measured otherwise

→ Next: [10 — Advanced Patterns](10-advanced-patterns.md)
