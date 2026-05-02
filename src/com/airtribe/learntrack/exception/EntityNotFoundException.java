package com.airtribe.learntrack.exception;

/**
 * Thrown when a requested entity (Student, Course, Enrollment) is not found.
 */
public class EntityNotFoundException extends Exception {

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String entityType, int id) {
        super(entityType + " with ID " + id + " was not found.");
    }
}
