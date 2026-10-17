# 11 — Hard Interview Q&A

Use this as a drill deck. Cover answers out loud. Star ones you miss; revisit in 48 hours.

---

### Q1. What is a functional interface? Can it have static/default methods?

**A:** An interface with exactly one abstract method (SAM). Yes — any number of `default`/`static`/`private` methods are allowed; they don't count toward the SAM. `@FunctionalInterface` is optional but makes the compiler enforce the rule.

---

### Q2. How does a lambda differ from an anonymous class?

**A:**
- Lambda implements a SAM; anonymous class can extend/implement richer types
- `this` in lambda = enclosing instance; in anonymous class = the anonymous instance
- Lambdas can't declare their own instance state the same way
- Bytecode: lambdas use `invokedynamic` (usually lighter); anonymous classes get synthetic classes

---

### Q3. What does "effectively final" mean? Why is it required?

**A:** A local variable whose value never changes after assignment. Lambdas/inner classes may outlive the stack frame; Java captures a stable copy of the local. Reassignment would make that capture inconsistent, so it's forbidden.

---

### Q4. `map` vs `flatMap` — explain with nested lists.

**A:** `map` transforms each element 1→1. `flatMap` transforms each element to a stream (0..N) and concatenates.  
`Stream<List<T>>` → `map(List::stream)` gives `Stream<Stream<T>>`; `flatMap(List::stream)` gives `Stream<T>`.

---

### Q5. Are intermediate operations lazy? Prove it.

**A:** Yes. Put a `println` inside `filter`/`map` and assign the stream to a variable without a terminal op — nothing prints. Call `collect`/`count` — prints appear. Elements are pulled by the terminal op.

---

### Q6. Why can you use a stream only once?

**A:** A stream is a consumable pipeline with internal state (spliterator position, linked ops). After a terminal op, it's spent. Reuse → `IllegalStateException`. Collections are reusable sources; create a new stream each time.

---

### Q7. `findFirst` vs `findAny`?

**A:** `findFirst` respects encounter order. `findAny` may return any element — freer in parallel. Prefer `findAny` when order doesn't matter and you parallelize.

---

### Q8. What does `allMatch` return on an empty stream? `anyMatch`? `noneMatch`?

**A:** `allMatch` → true, `noneMatch` → true (vacuous truth), `anyMatch` → false.

---

### Q9. How do you handle duplicate keys in `Collectors.toMap`?

**A:** Provide a merge function: `toMap(keyFn, valFn, (a,b) -> a)` or `Integer::sum`, etc. Without it, duplicates throw `IllegalStateException`.

---

### Q10. `groupingBy` vs `partitioningBy`?

**A:** `groupingBy` → `Map<K, List<T>>` for classifier `T→K`. `partitioningBy` → `Map<Boolean, List<T>>` for a predicate; always has both `true` and `false` keys.

---

### Q11. When is `reduce` wrong and `collect` right?

**A:** Building mutable structures (`List`, `StringBuilder`, `Map`) — use `collect`. `reduce` with mutable accumulators is error-prone in parallel. `reduce` shines for immutable associative folds (sum, max, immutable aggregate types).

---

### Q12. Why might parallel streams produce wrong results?

**A:** Non-associative reducers, race conditions on shared mutable state, ordering assumptions, using non-thread-safe collectors without concurrent variants.

---

### Q13. Is Optional meant for fields and parameters?

**A:** No. Primary use: **return types** to signal absence. Fields/params Optional is an anti-pattern (nullable Optional, serialization pain, API noise). Prefer empty collections over `Optional<List>`.

---

### Q14. `orElse` vs `orElseGet` — subtle bug?

**A:** `orElse(expr)` always evaluates `expr`. `orElseGet(supplier)` evaluates only if empty. Using `orElse(expensive())` wastes work / causes side effects every time.

---

### Q15. How do method references resolve for `String::length` vs `System.out::println`?

**A:** `String::length` is an **unbound** instance ref — receiver is the first lambda arg. `System.out::println` is a **bound** instance ref — receiver is the captured `PrintStream` instance `System.out`.

---

### Q16. Can lambdas throw checked exceptions?

**A:** Only if the SAM declares them (`Callable.call` does; `Function.apply` doesn't). Otherwise catch inside or wrap in unchecked / use a `ThrowingFunction` adapter.

---

### Q17. What's wrong with this?

```java
List<String> results = new ArrayList<>();
list.parallelStream().filter(...).forEach(results::add);
```

**A:** `ArrayList` isn't thread-safe; concurrent `add` races. Use `.collect(Collectors.toList())` / `.toList()` or a concurrent collection intentionally.

---

### Q18. Order of `filter` and `map` — does it matter?

**A:** Functionally for pure ops: filtering then mapping vs mapping then filtering (with adjusted predicate) can differ in work done. Performance: filter early to avoid expensive maps. Correctness: if `map` can throw on filtered-out values, filter first.

---

### Q19. Implement `joining` behavior without `Collectors.joining`.

**A:** Sketch: `Collector.of(StringBuilder::new, StringBuilder::append, StringBuilder::append, StringBuilder::toString)` — plus delimiter logic in accumulator/combiner, or reduce with care (quadratic string concat if naive).

---

### Q20. Infinite streams — how do you not hang?

**A:** Pair generators/`iterate` with short-circuit ops: `limit`, `takeWhile`, `findFirst`, `anyMatch`, etc. Never `collect` an infinite stream without a bound.

---

### Q21. What's `teeing` for?

**A:** Run two collectors in one pass and merge results (Java 12+). E.g., count + average without streaming twice.

---

### Q22. How would you get top-3 highest salaries per department?

**A:**

```java
employees.stream().collect(
  groupingBy(Employee::dept,
    collectingAndThen(
      toList(),
      list -> list.stream()
                  .sorted(Comparator.comparingInt(Employee::salary).reversed())
                  .limit(3)
                  .toList())));
```

Discuss alternatives: custom collector with bounded priority heap per group for huge data.

---

### Q23. Why prefer `IntStream.sum()` over `stream.map(Integer::intValue).reduce(0, Integer::sum)`?

**A:** Less boxing/unboxing; specialized pipeline; clearer intent; better performance on large numeric data.

---

### Q24. Explain stream pipeline fusion / "pull" model briefly.

**A:** Terminal op pulls elements from the source through the chain. Ops are fused so an element goes through multiple stages before the next element is pulled (enables short-circuit). You don't get N full passes for N intermediate ops.

---

### Q25. Design question: convert imperative nested loops that build a `Map<K, List<V>>` to streams. What do you watch for?

**A:** Use `groupingBy` / `groupingBy`+`mapping`. Watch: null keys/values, duplicate handling, mutability of resulting lists, parallel thread-safety, and whether encounter order matters.

---

## Spicy coding drills

Do these without an IDE first:

1. Frequency map of words in a sentence (`groupingBy` + `counting`)
2. Partition numbers into even/odd, then sum each partition
3. Flatten `List<List<T>>` and dedupe while preserving first-seen order
4. `Optional<User>` → `Optional<String>` email domain via `map`/`flatMap`
5. Custom `Collector` that builds an immutable `Map`
6. Rewrite a parallel-unsafe `forEach` mutation into a safe `collect`
7. Given orders with line items, total revenue via `flatMap`
8. Explain output order of `peek` + `filter` + `limit(2)` on `Stream.of(1..10)`

---

## Expert self-certification

You're "expert enough for hard interviews" when you can:

- [ ] Teach `flatMap` with two different examples (collections + Optional)
- [ ] Write nested `groupingBy` + downstream collector on a whiteboard
- [ ] Diagnose a parallel stream race from a code snippet in <60s
- [ ] Defend when *not* to use streams/Optional
- [ ] Convert freely: lambda ↔ method reference ↔ anonymous class
- [ ] Answer Q1–Q25 without notes at ≥80%

Return to weak modules. Re-do exercises in `exercises/`.
