# Design Notes

## Why ArrayList instead of Array?

Plain Java arrays have a fixed size that must be declared upfront (e.g. `Student[] students = new Student[100]`). In a real application we do not know in advance how many students or courses will be added. If we pick too small a size we crash with an `ArrayIndexOutOfBoundsException`; if we pick too large a size we waste memory.

`ArrayList` solves this by growing dynamically. Internally it manages its own array and resizes it automatically whenever capacity is exceeded. For a learning project with in-memory data, `ArrayList` gives us clean `add`, `get`, `set`, and iteration without manual index management. It also interacts well with the enhanced for-loop, making code more readable. Arrays remain useful when the size is truly fixed and performance is critical, but for this project `ArrayList` is the right choice.

---

## Where static Members Were Used and Why

Static fields and methods belong to the **class itself**, not to any particular instance. We used static members in two places:

**`IdGenerator`** — The counters `studentIdCounter`, `courseIdCounter`, and `enrollmentIdCounter` are `private static int` fields. Because they are static, all code in the application shares the same counter value. If they were instance fields, every new `IdGenerator` object would restart counting from 1, producing duplicate IDs. The methods `getNextStudentId()` etc. are also static so callers never need to create an `IdGenerator` object — they simply call `IdGenerator.getNextStudentId()`.

**`AppConstants` and `MenuOptions`** — All fields are `public static final`, making them compile-time constants. Static means one copy shared everywhere; final means the value never changes. This prevents "magic numbers" scattered across the codebase — if a menu option number needs to change, we update it in one place.

---

## Where Inheritance Was Used and What We Gained

`Student` extends `Person`. The fields `id`, `firstName`, `lastName`, and `email` are defined once in `Person`. Without inheritance, `Student` would have to declare and manage all four fields itself — and if we later added a `Trainer` class it would repeat the same fields a third time.

By using inheritance:
- Common fields live in one place (`Person`), reducing duplication.
- `Student` uses `super(id, firstName, lastName, email)` in its constructor to delegate initialization upward — showing how the `super` keyword works.
- `getDisplayName()` is declared in `Person` and **overridden** in `Student` to append batch information. This is a small but concrete demonstration of polymorphism: code that holds a `Person` reference gets the right behavior at runtime depending on the actual object type.

The same pattern would apply naturally if a `Trainer` class were added — it would extend `Person`, reuse the common fields, and override `getDisplayName()` in its own way without touching `Student` at all.
