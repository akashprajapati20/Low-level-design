package com.roombooking.exception;

/** Thrown when a referenced employee does not exist. */
public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(String employeeId) {
        super("No employee found with id: " + employeeId);
    }
}
