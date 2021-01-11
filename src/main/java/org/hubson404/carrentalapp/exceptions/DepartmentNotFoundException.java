package org.hubson404.carrentalapp.exceptions;

public class DepartmentNotFoundException extends RuntimeException {
    public DepartmentNotFoundException(Long message) {
        super("Could not find department with id: " + message);
    }
}
