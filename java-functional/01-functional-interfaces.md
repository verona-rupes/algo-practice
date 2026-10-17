# 01 — Functional Interfaces

## What is a functional interface?

A **functional interface** is an interface with **exactly one abstract method** (SAM — Single Abstract Method).

That single method is the *behavior slot* a lambda fills.

```java
@FunctionalInterface          // optional but recommended — compiler enforces SAM
public interface Predicate<T> {
    boolean test(T t);        // ← the one abstract method

    // default / static methods are ALLOWED and do NOT count toward the SAM
    default Predicate<T> and(Predicate<? super T> other) { ... }
    static <T> Predicate<T> isEqual(Object target) { ... }
}
```

`@FunctionalInterface` is documentation + compile-time guard. If you add a second abstract method, the code fails to compile.

## Why they exist

Before Java 8, "pass behavior" meant anonymous classes:

```java
Collections.sort(list, new Comparator<String>() {
    @Override
    public int compare(String a, String b) {
        return a.length() - b.length();
    }
});
```

Lambdas need a *target type*. Functional interfaces are that target type.

```java
Collections.sort(list, (a, b) -> a.length() - b.length());
//                     ↑ inferred as Comparator<String>
```

## The "Big Four" (memorize these)

| Interface | Abstract method | Shape | Typical use |
|-----------|-----------------|-------|-------------|
| `Predicate<T>` | `boolean test(T t)` | T → boolean | filter, validation |
| `Function<T,R>` | `R apply(T t)` | T → R | map, transform |
| `Consumer<T>` | `void accept(T t)` | T → void | forEach, side effects |
| `Supplier<T>` | `T get()` | () → T | factories, lazy values |

### Quick examples

```java
Predicate<String> notBlank = s -> s != null && !s.isBlank();
Function<String, Integer> length = String::length;
Consumer<String> printer = System.out::println;
Supplier<List<String>> empty = ArrayList::new;
```

## Important variants (know the names)

| Interface | Why it exists |
|-----------|---------------|
| `BiPredicate<T,U>` | two inputs → boolean |
| `BiFunction<T,U,R>` | two inputs → R |
| `BinaryOperator<T>` | `BiFunction<T,T,T>` — same type in/out (reduce!) |
| `UnaryOperator<T>` | `Function<T,T>` — same type in/out |
| `BiConsumer<T,U>` | two inputs, no return |
| `*ToInt/*ToLong/*ToDouble` | avoid boxing (`ToIntFunction<T>`) |
| `IntPredicate`, `IntFunction`, … | primitive specializations |

`BinaryOperator<T>` is the most interview-relevant special case — `Stream.reduce` and `Collectors.reducing` use it.

## Default & static methods — composition toolkit

Functional interfaces in `java.util.function` ship useful defaults:

```java
Predicate<String> longEnough = s -> s.length() >= 3;
Predicate<String> startsWithA = s -> s.startsWith("A");

Predicate<String> both = longEnough.and(startsWithA);
Predicate<String> either = longEnough.or(startsWithA);
Predicate<String> neither = both.negate();

Function<String, String> trim = String::trim;
Function<String, Integer> len = String::length;
Function<String, Integer> trimThenLen = trim.andThen(len);
Function<String, Integer> same = len.compose(trim); // apply trim first
```

**Composition rule of thumb**
- `f.andThen(g)` → do `f`, then `g` on the result
- `g.compose(f)` → same pipeline, different spelling

## Writing your own

```java
@FunctionalInterface
public interface TriFunction<A, B, C, R> {
    R apply(A a, B b, C c);
}

TriFunction<Integer, Integer, Integer, Integer> sum3 = (x, y, z) -> x + y + z;
```

When to write a custom FI:
- Domain language matters (`Validator`, `RetryPolicy`, `RateLimiter`)
- Arity doesn't exist in JDK (3+ args)
- You want named defaults for *your* domain

When *not* to:
- `Function` / `BiFunction` already say it clearly

## Target typing (critical mental model)

A lambda has **no type by itself**. The type comes from context:

```java
// Same lambda body, different target types
Predicate<String> p = s -> s.isEmpty();
Function<String, Boolean> f = s -> s.isEmpty(); // Boxing! Prefer Predicate
```

Contexts that provide a target type:
- Variable declaration / assignment
- Method parameter
- `return` statement
- Cast: `(Predicate<String>)(s -> s.isEmpty())`
- Ternary (both branches must share a target)

## Common interview traps

1. **"Is `Comparator` a functional interface?"**  
   Yes — `compare` is the SAM. `equals` doesn't count (it's already on `Object`).

2. **"Can a functional interface extend another?"**  
   Yes, as long as the *resulting* type still has exactly one abstract method.

3. **"Do default methods break the SAM rule?"**  
   No. Only abstract methods count.

4. **"Why not always use `Function<T,Boolean>` instead of `Predicate<T>`?"**  
   Boxing, less readable, loses `and`/`or`/`negate`.

## Checkpoint

Before module 02, you should be able to:
- Define SAM in one sentence
- List the Big Four and their method signatures from memory
- Explain what `@FunctionalInterface` does
- Compose two predicates with `and` / `or` / `negate`

→ Next: [02 — Lambdas](02-lambdas.md)
