# Become an Expert: Java Lambdas, Streams & Functional Interfaces

A progressive path from zero → interview-hard. Study in order. Each module builds on the last.

## How to use this

1. Read the module markdown (concepts + "why it matters").
2. Run / study the matching `.java` files under `examples/`.
3. Do the exercises in `exercises/` **before** peeking at `solutions/`.
4. After modules 01–10, grind `11-hard-interview-qa.md`.

## Curriculum map

| # | Module | You will be able to… |
|---|--------|----------------------|
| 01 | [Functional Interfaces](01-functional-interfaces.md) | Name the 4 core interfaces, write custom `@FunctionalInterface`s |
| 02 | [Lambdas](02-lambdas.md) | Write every lambda form; reason about effectively final captures |
| 03 | [Method References](03-method-references.md) | Choose between `::` forms and when *not* to use them |
| 04 | [Streams Foundation](04-streams-foundation.md) | Build streams, explain laziness, pipeline anatomy |
| 05 | [Intermediate Ops](05-intermediate-operations.md) | `map`/`flatMap`/`filter`/`distinct`/`sorted`/`peek` mastery |
| 06 | [Terminal Ops](06-terminal-operations.md) | Reduce, match, find, forEach — and when streams are wrong |
| 07 | [Collectors Deep Dive](07-collectors-deep-dive.md) | Grouping, partitioning, teeing, custom collectors |
| 08 | [Optional](08-optional.md) | Use Optional correctly (and avoid the classic anti-patterns) |
| 09 | [Parallel Streams](09-parallel-streams.md) | Know when parallel helps, hurts, and breaks correctness |
| 10 | [Advanced Patterns](10-advanced-patterns.md) | Composition, exception wrapping, stream vs loop tradeoffs |
| 11 | [Hard Interview Q&A](11-hard-interview-qa.md) | Answer tough design / gotcha / complexity questions |

## Mental model (keep this taped to your monitor)

```
Functional Interface  →  contract with ONE abstract method
        ↓
Lambda / Method Ref   →  implementation of that contract
        ↓
Stream pipeline       →  lazy chain of ops ending in ONE terminal op
        ↓
Collector / Optional  →  how results are shaped / absences handled
```

## Prerequisites

- Java 8+ basics (classes, interfaces, generics, collections)
- Comfort with `List`, `Map`, `Set`, and iteration

## Suggested pace

- **Crash (weekend):** Modules 01–06 + skim 07–08
- **Solid (1–2 weeks):** All modules + every exercise
- **Expert (ongoing):** Module 11 repeatedly; rewrite production loops as streams (and back)

## Quick self-check before claiming "expert"

- [ ] Draw a stream pipeline and mark which ops are intermediate vs terminal
- [ ] Explain why `map` vs `flatMap` and give an example of each
- [ ] Write `groupingBy` with a downstream collector from memory
- [ ] Explain "effectively final" with a broken-then-fixed example
- [ ] List 3 cases where a classic `for` loop beats a stream
- [ ] Explain why parallel streams can be slower *and* wrong
- [ ] State Optional's intended return-type role (not a field, not a param)
