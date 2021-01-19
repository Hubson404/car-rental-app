package org.hubson404.carrentalapp.exceptions;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(Long id) {
        super("Could not find employee with id: " + id);
    }

    public EmployeeNotFoundException(String message) {
        super(message);
    }
}
