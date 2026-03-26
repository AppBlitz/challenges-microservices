package com.employee_microservice.exception;

/**
 * Custom exception thrown when an error occurs during the aggregation 
 * or addition of a new employee to the system.
 * * <p>This is a checked exception because it extends {@link Exception}.</p>
 */
public class AgregationEmployee extends Exception {

    /**
     * Constructs a new exception with the specified detail message.
     * * @param message The specific error message that describes the 
     * failure (e.g., "Employee already exists").
     */
    public AgregationEmployee(String message) {
        super(message);
    }
}
