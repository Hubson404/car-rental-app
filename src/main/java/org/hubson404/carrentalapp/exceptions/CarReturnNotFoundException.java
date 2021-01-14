package org.hubson404.carrentalapp.exceptions;

public class CarReturnNotFoundException extends RuntimeException {
    public CarReturnNotFoundException(Long message) {
        super("Could not find carReturn with id: " + message);
    }
}
