# 03 — Method References

Method references are **syntactic sugar** for lambdas that only call an existing method.

```java
s -> s.toLowerCase()      ≡  String::toLowerCase
s -> System.out.println(s) ≡  System.out::println
() -> new ArrayList<>()   ≡  ArrayList::new
```

Use them when the lambda adds **no extra logic**.

## The four kinds (memorize)

| Kind | Syntax | Lambda equivalent |
|------|--------|-------------------|
| Static | `Class::staticMethod` | `x -> Class.staticMethod(x)` |
| Bound instance | `instance::method` | `x -> instance.method(x)` |
| Unbound instance | `Class::instanceMethod` | `(obj, args…) -> obj.method(args…)` |
| Constructor | `Class::new` | `args -> new Class(args)` |

### 1. Static method reference

```java
Function<String, Integer> parse = Integer::parseInt;
// ≡ s -> Integer.parseInt(s)

ToIntFunction<String> parse2 = Integer::parseInt;
```

### 2. Bound instance method reference

The receiver is fixed (captured):

```java
String prefix = "Hello ";
Function<String, String> greet = prefix::concat;
// ≡ s -> prefix.concat(s)

Consumer<String> printer = System.out::println;
```

### 3. Unbound instance method reference

The receiver is the **first argument**:

```java
Function<String, Integer> len = String::length;
// ≡ s -> s.length()

BiFunction<String, String, Boolean> starts = String::startsWith;
// ≡ (s, prefix) -> s.startsWith(prefix)

Comparator<String> ignoreCase = String::compareToIgnoreCase;
```

This is the form people confuse most. Ask: *"Is the left of `::` an instance or a class?"*

### 4. Constructor references

```java
Supplier<List<String>> lists = ArrayList::new;
Function<Integer, int[]> arrays = int[]::new;          // array constructor
BiFunction<String, Integer, Person> people = Person::new;
```

Works for any constructor arity that matches a FI SAM.

## Choosing lambda vs method reference

| Prefer method ref when… | Prefer lambda when… |
|-------------------------|---------------------|
| Exact existing method call | You transform args (`s -> s.trim().toLowerCase()`) |
| Intent is crystal clear | You need null checks or branching |
| Team style favors `::` | Capture would be awkward |

```java
// Method ref is worse here — logic isn't "just a call"
list.stream().map(s -> s == null ? "" : s.trim());

// Method ref shines
list.stream().map(String::trim).map(String::toUpperCase);
```

## Ambiguity & overloads

Sometimes `::` is ambiguous across overloads / FI types:

```java
// May need a cast or explicit lambda to disambiguate
ExecutorService pool = ...;
pool.submit(this::task); // Runnable vs Callable overload ambiguity possible
pool.submit((Runnable) this::task);
```

If inference fails, fall back to an explicit lambda — clarity > cleverness.

## Chaining with composition

Method refs compose through FI defaults:

```java
Function<String, String> normalize =
    ((Function<String, String>) String::trim)
        .andThen(String::toLowerCase);
```

(The cast helps when the compiler can't pick the FI type for `String::trim` alone.)

## Checkpoint

- Name all four kinds of method references with examples
- Convert freely: lambda ↔ method reference
- Explain bound vs unbound instance refs in one sentence each

→ Next: [04 — Streams Foundation](04-streams-foundation.md)
