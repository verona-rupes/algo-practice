# 08 — Optional

`Optional<T>` is a **container that may or may not hold a non-null value**. It was designed primarily as a **return type** to make absence explicit.

## Creating Optionals

```java
Optional.empty();
Optional.of(value);           // NPE if value is null
Optional.ofNullable(value);   // empty if null
```

## Querying & transforming (prefer these over `isPresent`+`get`)

```java
opt.isPresent();              // or isEmpty() Java 11+
opt.ifPresent(System.out::println);
opt.ifPresentOrElse(System.out::println, () -> System.out.println("none"));

opt.map(String::toUpperCase);
opt.flatMap(this::findDetail);   // when mapper already returns Optional
opt.filter(s -> s.length() > 3);
opt.or(() -> Optional.of("default"));  // Java 9+
```

## Unwrapping

```java
T v1 = opt.get();                         // avoid — throws if empty
T v2 = opt.orElse(defaultValue);          // eager default
T v3 = opt.orElseGet(this::expensive);    // lazy default
T v4 = opt.orElseThrow();                 // Java 10+
T v5 = opt.orElseThrow(IllegalStateException::new);
```

**Rule:** prefer `orElseGet` when the default is expensive or has side effects. `orElse` **always** evaluates its argument.

```java
opt.orElse(loadFromDb());      // DB always hit — bad
opt.orElseGet(this::loadFromDb); // DB hit only if empty — good
```

## Optional + streams

```java
// Java 9+
stream.map(this::findOpt).flatMap(Optional::stream)...

// Older idiom
stream.map(this::findOpt)
      .filter(Optional::isPresent)
      .map(Optional::get)...
```

## Anti-patterns (say these in interviews)

| Don't | Do |
|-------|-----|
| Use Optional as a field | Use nullable field / Null Object / separate type |
| Pass Optional as a method parameter | Overload methods or use `@Nullable` + docs |
| `Optional.of(maybeNull)` | `ofNullable` |
| `isPresent()` + `get()` everywhere | `map` / `flatMap` / `orElse*` |
| `Optional<Optional<T>>` | `flatMap` |
| `Optional` wrapping collections | Return empty collection |

```java
// BAD
public void setName(Optional<String> name) { ... }

// GOOD
public Optional<String> findName(Id id) { ... }  // return type
```

## Primitive optionals

`OptionalInt`, `OptionalLong`, `OptionalDouble` avoid boxing — returned by primitive stream terminals like `max()`, `findFirst()`.

## Checkpoint

- Prefer `map`/`flatMap`/`orElseGet` over `get`
- Explain why Optional is for return types
- Avoid Optional parameters/fields in API design

→ Next: [09 — Parallel Streams](09-parallel-streams.md)
