package com.softserve.itacademy.exception.task;

import com.softserve.itacademy.model.Priority;

public class InvalidPriorityFormatException extends RuntimeException{
    public InvalidPriorityFormatException(Priority priority) {
        super("Priority " + priority + " is not a valid priority");
    }
}
