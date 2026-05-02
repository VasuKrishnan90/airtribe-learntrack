package com.airtribe.learntrack.entity;

/**
 * Represents a student in the LearnTrack system.
 * Extends Person (inheritance) and adds student-specific fields.
 * Demonstrates constructor overloading.
 */
public class Student extends Person {

    private String batch;
    private boolean active;

    // Default constructor
    public Student() {
        super();
        this.active = true;
    }

    // Constructor WITHOUT email — demonstrates constructor overloading
    public Student(int id, String firstName, String lastName, String batch) {
        super(id, firstName, lastName, null);
        this.batch  = batch;
        this.active = true;
    }

    // Constructor WITH email — full details
    public Student(int id, String firstName, String lastName, String email, String batch) {
        super(id, firstName, lastName, email);
        this.batch  = batch;
        this.active = true;
    }

    /**
     * Overrides Person.getDisplayName() to include batch info.
     * Demonstrates method overriding / polymorphism.
     */
    @Override
    public String getDisplayName() {
        return super.getDisplayName() + " [Batch: " + batch + "]";
    }

    // ---------- Getters & Setters ----------

    public String getBatch() { return batch; }
    public void setBatch(String batch) { this.batch = batch; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    @Override
    public String toString() {
        return String.format("Student{id=%d, name='%s', email='%s', batch='%s', active=%b}",
                getId(), getDisplayName(), getEmail(), batch, active);
    }
}
