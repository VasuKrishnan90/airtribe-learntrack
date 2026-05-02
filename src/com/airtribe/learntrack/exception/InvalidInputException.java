package com.airtribe.learntrack.exception;

/**
 * Thrown when user-provided input fails validation.
 */
public class InvalidInputException extends Exception {

    public InvalidInputException(String message) {
        super(message);
    }
}
