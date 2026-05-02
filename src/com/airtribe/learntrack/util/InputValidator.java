package com.airtribe.learntrack.util;

import com.airtribe.learntrack.exception.InvalidInputException;

/**
 * Utility class for validating user input.
 * All methods are static — no instance needed.
 */
public class InputValidator {

    private InputValidator() {}

    /**
     * Ensures a string is not null or blank.
     */
    public static void requireNonBlank(String value, String fieldName) throws InvalidInputException {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidInputException(fieldName + " cannot be blank.");
        }
    }

    /**
     * Ensures an integer is positive (> 0).
     */
    public static void requirePositive(int value, String fieldName) throws InvalidInputException {
        if (value <= 0) {
            throw new InvalidInputException(fieldName + " must be a positive number.");
        }
    }

    /**
     * Basic email format check.
     */
    public static void requireValidEmail(String email) throws InvalidInputException {
        if (email == null || !email.contains("@") || !email.contains(".")) {
            throw new InvalidInputException("Invalid email format: " + email);
        }
    }
}
