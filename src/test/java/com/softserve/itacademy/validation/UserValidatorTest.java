package com.softserve.itacademy.validation;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(JUnitPlatform.class)
public class UserValidatorTest {

    @Test
    void testIsValidFirstName() {
        assertTrue(UserValidator.isValidFirstName("John")); // Valid first name
        assertFalse(UserValidator.isValidFirstName("J"));   // Too short
        assertFalse(UserValidator.isValidFirstName("Jo123")); // Contains numbers
        assertFalse(UserValidator.isValidFirstName(""));    // Empty string
    }
}
