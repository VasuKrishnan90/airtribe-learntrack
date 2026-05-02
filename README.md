# LearnTrack — Student & Course Management System

A console-based Student & Course Management System built with **Core Java**, designed to practise fundamental OOP concepts.

---

## Project Description

LearnTrack lets admins manage **Students**, **Courses**, and **Enrollments** through an interactive menu-driven console interface. All data is stored in-memory (no database required).

---

## Directory Structure

```
src/
└── com/airtribe/learntrack/
    ├── Main.java                        ← Menu UI & application entry point
    ├── entity/
    │   ├── Person.java                  ← Base class (inheritance root)
    │   ├── Student.java                 ← Extends Person
    │   ├── Course.java
    │   └── Enrollment.java
    ├── repository/                      ← In-memory data layer (ArrayList)
    │   ├── StudentRepository.java
    │   ├── CourseRepository.java
    │   └── EnrollmentRepository.java
    ├── service/                         ← Business logic layer
    │   ├── StudentService.java
    │   ├── CourseService.java
    │   └── EnrollmentService.java
    ├── exception/
    │   ├── EntityNotFoundException.java
    │   └── InvalidInputException.java
    ├── util/
    │   ├── IdGenerator.java             ← Static ID counters
    │   └── InputValidator.java
    ├── constants/
    │   ├── AppConstants.java
    │   └── MenuOptions.java
    └── enums/
        ├── EnrollmentStatus.java        ← ACTIVE / COMPLETED / CANCELLED
        └── CourseStatus.java            ← ACTIVE / INACTIVE
docs/
    └── Design_Notes.md
```

---

## Features

| Module      | Features |
|-------------|----------|
| **Student** | Add, View All, Search by ID, Update, Deactivate (soft-delete) |
| **Course**  | Add, View All, Search by ID, Update, Activate/Deactivate toggle |
| **Enrollment** | Enroll student, View by student, View all, Mark Completed, Cancel |

---

## Java Concepts Demonstrated

- **Encapsulation** — private fields + public getters/setters in all entities
- **Inheritance** — `Student` extends `Person`
- **Polymorphism** — `getDisplayName()` overridden in `Student`
- **Constructor Overloading** — `Student` and `Course` have multiple constructors
- **Static members** — `IdGenerator` uses static counters and methods
- **ArrayList** — used in all repositories for dynamic storage
- **Custom Exceptions** — `EntityNotFoundException`, `InvalidInputException`
- **Enums** — `EnrollmentStatus`, `CourseStatus`
- **Packages** — clean separation: entity / repository / service / ui / util / exception / constants / enums

Code commited