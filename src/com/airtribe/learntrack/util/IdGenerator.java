package com.airtribe.learntrack.util;

/**
 * Utility class for generating unique IDs for each entity type.
 * Demonstrates use of static fields and static methods.
 * All members are static — no instance of this class is needed.
 */
public class IdGenerator {

    // Static counters — shared across all usages (class-level state)
    private static int studentIdCounter    = 1;
    private static int courseIdCounter     = 1;
    private static int enrollmentIdCounter = 1;

    // Private constructor — prevents instantiation of this utility class
    private IdGenerator() {}

    public static int getNextStudentId() {
        return studentIdCounter++;
    }

    public static int getNextCourseId() {
        return courseIdCounter++;
    }

    public static int getNextEnrollmentId() {
        return enrollmentIdCounter++;
    }
}
