package com.softserve.itacademy.validation;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(JUnitPlatform.class)
public class UserValidatorTest {

    @Test
    void testIsValidName() {
        assertTrue(UserValidator.isValidFirstName("John"));     // Valid name
        assertFalse(UserValidator.isValidFirstName("J"));       // Too short
        assertFalse(UserValidator.isValidFirstName("Jo123"));   // Contains numbers
        assertFalse(UserValidator.isValidFirstName(""));        // Empty string
    }

    @Test
    void testIsValidEmail() {
        assertTrue(UserValidator.isValidEmail("john@doe.com")); // Valid email
        assertFalse(UserValidator.isValidEmail("johndoe.com")); // Missing '@'
        assertFalse(UserValidator.isValidEmail("john@.com"));   // Missing domain name
        assertFalse(UserValidator.isValidEmail("john@com"));    // Missing '.'
        assertFalse(UserValidator.isValidEmail(""));            // Empty string
    }

    @Test
    void testIsValidPassword() {
        assertTrue(UserValidator.isValidPassword("password1"));  // Valid password
        assertTrue(UserValidator.isValidPassword("12345678"));   // All digits is Valid
        assertTrue(UserValidator.isValidPassword("abcABC123"));  // Letters and digits is Valid
        assertFalse(UserValidator.isValidPassword("pass"));      // Too short
        assertFalse(UserValidator.isValidPassword("pass!@#"));   // Contains invalid symbols
        assertFalse(UserValidator.isValidPassword(""));          // Empty string
    }
}