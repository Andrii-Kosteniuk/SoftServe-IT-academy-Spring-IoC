package com.softserve.itacademy.exception.user;

public class InvalidPasswordFormatException extends RuntimeException {
    public InvalidPasswordFormatException() {
        super("Password must be at least 8 characters long and contain only letters and/or digits.");
    }
}
