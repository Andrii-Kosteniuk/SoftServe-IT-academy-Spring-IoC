package com.softserve.itacademy.exception.user;

public class InvalidEmailFormatException extends RuntimeException {
    public InvalidEmailFormatException() {
        super("Email must be in the format 'example@domain.com' and contain only letters.");
    }
}
