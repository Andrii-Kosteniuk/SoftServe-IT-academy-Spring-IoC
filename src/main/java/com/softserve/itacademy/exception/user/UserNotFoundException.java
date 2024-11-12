package com.softserve.itacademy.exception.user;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String email) {
        super("User with email " + email + " not found");
    }
}
