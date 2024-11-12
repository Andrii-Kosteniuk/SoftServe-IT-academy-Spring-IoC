package com.softserve.itacademy.validation;

public class UserValidator {

    public static boolean isValidFirstName(String firstName) {
        return validateName(firstName);
    }

    public static boolean isValidLastName(String lastName) {
        return validateName(lastName);
    }

    public static boolean isValidEmail(String email) {
        return validateEmail(email);
    }

    public static boolean isValidPassword(String password) {
        return validatePassword(password);
    }

    private static boolean validateName(String name) {
        return name.matches("[a-zA-Z]{2,}");
    }

    private static boolean validateEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+(?:\\.[A-Za-z0-9-]+)*\\.[A-Za-z]{2,}$";
        return email.matches(regex);
    }

    private static boolean validatePassword(String password) {
        return password.matches("[a-zA-Z0-9]{8,}");
    }
}
