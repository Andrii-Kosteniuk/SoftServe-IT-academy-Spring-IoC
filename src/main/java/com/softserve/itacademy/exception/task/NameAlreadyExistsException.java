package com.softserve.itacademy.exception.task;

public class NameAlreadyExistsException extends RuntimeException {
    public NameAlreadyExistsException(String name) {
        super("User with name " + name + " already exists");
    }
}
