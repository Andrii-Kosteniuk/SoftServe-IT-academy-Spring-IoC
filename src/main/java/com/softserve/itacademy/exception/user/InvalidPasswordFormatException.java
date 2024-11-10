package com.softserve.itacademy.exception.user;

public class InvalidPasswordFormatException extends RuntimeException {
    public InvalidPasswordFormatException(String message) {
        super(message);
    }
}
