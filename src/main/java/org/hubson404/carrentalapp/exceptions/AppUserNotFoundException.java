package org.hubson404.carrentalapp.exceptions;

public class AppUserNotFoundException extends RuntimeException {

    public AppUserNotFoundException(String email) {
        super("Could not find user with email: " + email);
    }
}
