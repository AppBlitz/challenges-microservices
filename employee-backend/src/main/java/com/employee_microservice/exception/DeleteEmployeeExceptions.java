package com.employee_microservice.exception;

/**
 * Custom exception thrown when a failure occurs during the removal
 * or deletion of an employee from the system.
 * *
 * <p>
 * This class provides a specific error type for the delete operation,
 * allowing for more granular error handling.
 * </p>
 */
public class DeleteEmployeeExceptions extends Exception {

    /**
     * Constructs a new exception with a specific detail message.
     * * @param message The descriptive text explaining why the deletion
     * process failed.
     */
    public DeleteEmployeeExceptions(String message) {
        super(message);
    }
}
