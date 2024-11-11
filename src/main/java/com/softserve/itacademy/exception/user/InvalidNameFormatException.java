package com.softserve.itacademy.exception.user;

public class InvalidNameFormatException extends RuntimeException {
    public InvalidNameFormatException() {
        super("Name must contain at least 2 letters.");
    }
}
