# MODULE 1: Introduction to Programming Paradigms

## What is a Programming Paradigm?

A **programming paradigm** is a style or way of thinking about how to write code. It gives you a **mental model** for solving problems using a particular structure.

Different paradigms solve the same problem in different ways, just like different people might give different instructions to get to the same place.

---

## 🔸 Real-World Analogy: Making a Cup of Tea

Imagine we want to make a cup of tea. Different paradigms would approach this differently.

---

### 🛠️ 1. **Imperative Paradigm** — Step-by-step instructions

“Go to the kitchen, turn on the stove, pour water into a pot, heat it, add tea leaves, pour into cup.”

- You **tell the computer exactly what to do and how to do it**.
- **Focus**: *How* to do things
- **Example languages**: C, Java, Python (can be imperative)

✅ **Used when**: You need control over each step.

---

### 🧱 2. **Object-Oriented Paradigm (OOP)** — Model it like the real world

“I have a `Kettle` object that knows how to boil water. I also have a `Cup` object. I just ask the objects to do their jobs.”

```java
Kettle kettle = new Kettle();
kettle.boil();

Cup cup = new Cup();
cup.pour(kettle.getHotWater());
```

- You **model the world using objects with data (state) and behavior (methods)**.
- **Focus**: Who does the work (objects)
- **Example languages**: Java, C++, Python, Ruby

✅ **Used when**: You’re modeling real-world systems like banking, games, etc.

---

### 🌿 3. Functional Paradigm — Transform inputs to outputs, no side effects

“Given tea leaves and hot water, return tea. Don’t touch the kitchen. Just pure inputs and outputs.”

```javascript
const makeTea = (leaves, water) => combine(leaves, water);
```

- You use **pure functions with no side effects**.
- Avoid **changing variables** or **shared state** (Multiple parts of your program can see or modify the same data). 
- **Focus**: What to do, not how
- **Example languages**: Haskell, Scala, Elixir, JavaScript (supports it), Python (partially)

✅ **Used when**: You want easy-to-test, predictable code — especially in data pipelines, streaming, or parallel processing.

### 🕵️‍♂️ 4. Logic Paradigm — Declare facts and rules, let the system figure it out

“Tea is made if you have hot water and tea leaves. The system figures out how.”

```java
has(hot_water).
has(tea_leaves).
can_make_tea :- has(hot_water), has(tea_leaves).
```

- You **describe rules and relationships, not procedures**.
- **Focus**: What is true, not how to compute
- **Example languages**: Prolog

✅ **Used when**: Solving puzzles, AI reasoning, expert systems.


### 📝 5. Declarative Paradigm — State the goal, not the steps

“I want tea.”
(Behind the scenes: the system figures out how to get hot water, steep it, etc.)

- SQL is a great example:

```sql
SELECT name FROM drinks WHERE type = 'tea';
```

- You **declare what you want, not how to do it**.
- **Focus**: Desired outcome
- **Example languages**: SQL, HTML, CSS, many domain-specific languages

✅ **Used when**: Querying databases, UI layout, configuration files.

## 🎯 Summary Table


| Paradigm        | Focus                | Real-World Analogy                        | Best Use Case                        |
| --------------- | -------------------- | ----------------------------------------- | ------------------------------------ |
| Imperative      | *How* to do it       | Recipe with step-by-step instructions     | Low-level logic, control flow        |
| Object-Oriented | *Who* does it        | Different objects handling their parts    | Modeling systems with behavior       |
| Functional      | *What* to compute    | Input → output without side effects       | Data processing, concurrency         |
| Logic           | *What is true*       | Rules and facts; system infers everything | AI, inference engines                |
| Declarative     | *What* you want done | You specify the goal, not the path        | Databases, UI, infrastructure config |
