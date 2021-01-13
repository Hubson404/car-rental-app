package org.hubson404.carrentalapp.exceptions;

public class CarReservationNotFoundException extends RuntimeException {
    public CarReservationNotFoundException(Long message) {
        super("Could not find CarReservation with id: " + message);
    }
}
