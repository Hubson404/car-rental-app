package org.hubson404.carrentalapp.exceptions;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(Long message) {
        super("Could not find customer with id: " + message);
    }
}
