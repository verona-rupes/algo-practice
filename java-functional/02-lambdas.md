# 02 — Lambdas

## Anatomy

```
(parameters) -> body
```

| Form | Example |
|------|---------|
| No params | `() -> "hi"` |
| One param (parens optional) | `s -> s.length()` or `(s) -> s.length()` |
| Multiple params | `(a, b) -> a + b` |
| Typed params | `(String a, String b) -> a.compareTo(b)` |
| Block body | `s -> { System.out.println(s); return s.length(); }` |
| Expression body | `s -> s.length()` — `return` is implicit |

Rules:
- Block body **needs** `return` if the FI expects a value
- Expression body **must not** use `return`
- Types are usually inferred; write them when inference fails or clarity wins

## Lambdas are implementations of functional interfaces

```java
Runnable r = () -> System.out.println("tick");
Callable<Integer> c = () -> 42;
Comparator<String> byLen = (a, b) -> Integer.compare(a.length(), b.length());
```

The compiler:
1. Finds the target FI type
2. Checks the lambda's arity/return match the SAM
3. Desugars to invokedynamic (not an anonymous class under the hood)

## Effectively final — the #1 gotcha

Lambdas may capture local variables only if those variables are **final or effectively final** (never reassigned after initialization).

```java
int factor = 2;
Function<Integer, Integer> mul = x -> x * factor; // OK — factor never reassigned

factor = 3; // now the lambda above would NOT compile if this line existed before use
```

**Why?** The lambda may outlive the stack frame. Java copies the value (for primitives / references) rather than creating a mutable shared local. Reassignment would make that model lie.

### Workarounds (and when they're smell)

```java
// 1. One-element array / AtomicInteger — legal, often a design smell
int[] box = {0};
list.forEach(x -> box[0] += x);

// 2. Prefer stream reduction / collector instead of mutating outside
int sum = list.stream().mapToInt(Integer::intValue).sum();
```

Instance/static fields **can** be mutated from lambdas (they're not locals). That doesn't make it a good idea for stream pipelines.

## `this` inside a lambda

Unlike anonymous classes, `this` in a lambda refers to the **enclosing class**, not the lambda itself.

```java
class Demo {
    String name = "outer";

    void show() {
        Runnable anon = new Runnable() {
            String name = "anon";
            public void run() {
                System.out.println(this.name); // "anon"
            }
        };
        Runnable lambda = () -> System.out.println(this.name); // "outer"
    }
}
```

## Exceptions in lambdas

SAM methods that don't declare checked exceptions **cannot** throw checked exceptions from the lambda body unless you catch them inside.

```java
// Function.apply does not declare IOException
Function<String, String> bad = path -> Files.readString(Path.of(path)); // COMPILE ERROR

Function<String, String> ok = path -> {
    try {
        return Files.readString(Path.of(path));
    } catch (IOException e) {
        throw new UncheckedIOException(e);
    }
};
```

See module 10 for a reusable `ThrowingFunction` wrapper pattern.

## Shadowing & parameter names

Lambda parameters must not shadow local variables in the enclosing scope:

```java
String s = "outer";
// Predicate<String> p = s -> s.isEmpty(); // COMPILE ERROR — s already in scope
Predicate<String> p = str -> str.isEmpty(); // OK
```

## When *not* to use a lambda

- Body is long / multi-concern → extract a named method + method reference
- You need early `break`/`continue` across complex control flow → classic loop
- Debugging clarity matters more than terseness

```java
// Prefer readable named method once logic grows
list.stream().filter(this::isEligibleCustomer)...
```

## Lambda ↔ anonymous class equivalence

Anything a lambda can express, an anonymous class can too — but not vice versa:
- Anonymous classes can extend classes, declare fields, have multiple methods
- Lambdas only implement a SAM

## Checkpoint

- Write all six lambda forms from memory
- Explain effectively final with a code example
- Predict what `this` means in a lambda vs anonymous class
- Fix a lambda that needs to throw `IOException`

→ Next: [03 — Method References](03-method-references.md)
