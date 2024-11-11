package com.softserve.itacademy;

import com.softserve.itacademy.exception.user.InvalidEmailFormatException;
import com.softserve.itacademy.exception.user.InvalidNameFormatException;
import com.softserve.itacademy.exception.user.InvalidPasswordFormatException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(JUnitPlatform.class)
public class UserServiceTest {

    private static UserService userService;
    private User newUser;

    @BeforeAll
    public static void setupBeforeClass() throws Exception {
        AnnotationConfigApplicationContext annotationConfigContext = new AnnotationConfigApplicationContext(Config.class);
        userService = annotationConfigContext.getBean(UserService.class);
        annotationConfigContext.close();
    }

    @BeforeEach
    void setUpUser() {
        newUser = new User();
        newUser.setFirstName("John");
        newUser.setLastName("Doe");
        newUser.setEmail("john@doe.com");
        newUser.setPassword("password");


    }

    @Test
    public void testAddUser_Success() {
        User expectedUser = new User("John", "Doe",
                "john@doe.com", "password", null);

        User actualUser = userService.addUser(newUser);

        assertEquals(expectedUser, actualUser, "Users should be equal");

        Optional<User> foundUser = userService.findUserByEmail(actualUser.getEmail());
        assertTrue(foundUser.isPresent(), "User not found");
        assertEquals(expectedUser, foundUser.get(), "Users should be equal");
        userService.deleteUserByEmail(actualUser.getEmail());
    }

    @Test
    public void testAddUserFail_UserIsNull() {
        newUser = null;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.addUser(newUser));

        assertEquals("User cannot be null", exception.getMessage());
        assertTrue(userService.getAll().isEmpty(), "Users should be empty");
    }

    @Test
    public void testAddUserFail_FirstNameIsNull() {
        newUser.setFirstName(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.addUser(newUser));

        assertEquals("First name cannot be null", exception.getMessage());
        assertTrue(userService.getAll().isEmpty(), "Users should be empty");
    }

    @Test
    public void testAddUserFail_LastNameIsNull() {
        newUser.setLastName(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.addUser(newUser));

        assertEquals("Last name cannot be null", exception.getMessage());
        assertTrue(userService.getAll().isEmpty(), "Users should be empty");
    }

    @Test
    public void testAddUserFail_InvalidNameFormat() {
        newUser.setFirstName("J"); // too short
        newUser.setLastName(""); // empty

        InvalidNameFormatException exception = assertThrows(InvalidNameFormatException.class, () -> userService.addUser(newUser));

        assertEquals("Name must contain at least 2 letters.", exception.getMessage());
        assertTrue(userService.getAll().isEmpty(), "Users should be empty");
    }

    @Test
    public void testAddUserFail_EmailIsNull() {
        newUser.setEmail(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.addUser(newUser));

        assertEquals("Email cannot be null", exception.getMessage());
        assertTrue(userService.getAll().isEmpty(), "Users should be empty");
    }

    @Test
    public void testAddUserFail_InvalidEmail() {
        newUser.setEmail("invalid@Email");

        InvalidEmailFormatException exception = assertThrows(InvalidEmailFormatException.class, () -> userService.addUser(newUser));

        assertEquals("Email must be in the format 'example@domain.com' and contain only letters.", exception.getMessage());
        assertTrue(userService.getAll().isEmpty(), "Users should be empty");
    }

    @Test
    public void testAddUserFail_InvalidPasswordFormat() {
        newUser.setPassword("!nv@l!d");

        InvalidPasswordFormatException exception = assertThrows(InvalidPasswordFormatException.class, () -> userService.addUser(newUser));

        assertEquals(exception.getMessage(), "Password must be at least 8 characters long and contain only letters and/or digits.");
        assertTrue(userService.getAll().isEmpty(), "Users should be empty");
    }

    @Test
    public void testAddUserFail_PasswordIsNull() {
        newUser.setPassword(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.addUser(newUser));

        assertEquals("Password cannot be null", exception.getMessage());
        assertTrue(userService.getAll().isEmpty(), "Users should be empty");
    }
}
