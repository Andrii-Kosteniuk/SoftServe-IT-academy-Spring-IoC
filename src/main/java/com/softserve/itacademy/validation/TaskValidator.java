package com.softserve.itacademy.validation;

import com.softserve.itacademy.model.Priority;

public class TaskValidator {
    public static boolean isValidName(String name) {
        return name.matches("[a-zA-Z0-9]{2,}");
    }

    public static boolean isValidPriority(Priority priority) {
        return Priority.LOW.equals(priority) || Priority.MEDIUM.equals(priority) || Priority.HIGH.equals(priority) ;
    }
}
