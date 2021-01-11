package org.hubson404.carrentalapp.exceptions;

public class CarNotFoundException extends RuntimeException {
    public CarNotFoundException(Long message) {
        super("Could not find car with id: " + message);
    }
}
