package org.hubson404.carrentalapp.exceptions;

import org.hubson404.carrentalapp.domain.enums.EmployeePosition;

public class IllegalEmployeeIdException extends RuntimeException {
    public IllegalEmployeeIdException(Long id, EmployeePosition position) {
        super("Could not find employee by given id [ " + id + " ] or employee position is already set to " + position);
    }
}
