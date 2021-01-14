package org.hubson404.carrentalapp.exceptions;

public class CarRentalNotFoundException extends RuntimeException {
    public CarRentalNotFoundException(Long message) {
        super("Could not find carRental with id: " + message);
    }
}
