package com.softserve.itacademy.exception.task;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String name) {
        super("Task with name " + name + " not found");
    }
}
