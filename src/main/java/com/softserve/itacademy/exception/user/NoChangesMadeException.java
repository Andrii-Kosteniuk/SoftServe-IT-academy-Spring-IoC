package com.softserve.itacademy.exception.user;

public class NoChangesMadeException extends RuntimeException {
    public NoChangesMadeException() {
        super("No changes have been made.");
    }
}
