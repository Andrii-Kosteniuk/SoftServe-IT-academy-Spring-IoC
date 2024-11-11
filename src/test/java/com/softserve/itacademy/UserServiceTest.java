package com.softserve.itacademy;

import com.softserve.itacademy.exception.user.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.UserService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
    void testAddUser_Success() {
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
    void testAddUserFail_UserIsNull() {
        newUser = null;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.addUser(newUser));

        assertEquals("User cannot be null", exception.getMessage());
        assertTrue(userService.getAll().isEmpty(), "Users should be empty");
    }

    @Test
    void testAddUserFail_FirstNameIsNull() {
        newUser.setFirstName(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.addUser(newUser));

        assertEquals("First name cannot be null", exception.getMessage());
        assertTrue(userService.getAll().isEmpty(), "Users should be empty");
    }

    @Test
    void testAddUserFail_LastNameIsNull() {
        newUser.setLastName(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.addUser(newUser));

        assertEquals("Last name cannot be null", exception.getMessage());
        assertTrue(userService.getAll().isEmpty(), "Users should be empty");
    }

    @Test
    void testAddUserFail_InvalidNameFormat() {
        newUser.setFirstName("J"); // too short
        newUser.setLastName(""); // empty

        InvalidNameFormatException exception = assertThrows(InvalidNameFormatException.class, () -> userService.addUser(newUser));

        assertEquals("Name must contain at least 2 letters.", exception.getMessage());
        assertTrue(userService.getAll().isEmpty(), "Users should be empty");
    }

    @Test
    void testAddUserFail_EmailIsNull() {
        newUser.setEmail(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.addUser(newUser));

        assertEquals("Email cannot be null", exception.getMessage());
        assertTrue(userService.getAll().isEmpty(), "Users should be empty");
    }

    @Test
    void testAddUserFail_InvalidEmail() {
        newUser.setEmail("invalid@Email");

        InvalidEmailFormatException exception = assertThrows(InvalidEmailFormatException.class, () -> userService.addUser(newUser));

        assertEquals("Email must be in the format 'example@domain.com' and contain only letters.", exception.getMessage());
        assertTrue(userService.getAll().isEmpty(), "Users should be empty");
    }

    @Test
    void testAddUserFail_InvalidPasswordFormat() {
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

    @Test
    void testUpdateUser_Success() {
        User userToBeUpdated = new User("Jane", "Smith", "jane@smith.com", "newPassword", null);
        User existingUser = userService.addUser(newUser);
        String existingEmail = existingUser.getEmail();

        User updatedUser = userService.updateUserByEmail(existingEmail, userToBeUpdated);

        assertNotNull(updatedUser, "Updated user should not be null");
        assertEquals(userToBeUpdated.getFirstName(), updatedUser.getFirstName(), "First names should match");
        assertEquals(userToBeUpdated.getLastName(), updatedUser.getLastName(), "Last names should match");
        assertEquals(userToBeUpdated.getPassword(), updatedUser.getPassword(), "Passwords should match");
        assertNull(updatedUser.getMyTodos());

        Optional<User> foundUser = userService.findUserByEmail(updatedUser.getEmail());
        assertTrue(foundUser.isPresent(), "User not found after update");
        assertEquals(updatedUser, foundUser.get(), "Updated user should match the found user");

        userService.deleteUserByEmail(updatedUser.getEmail());
    }

    @Test
    void testUpdateEmail_Success() {

        User userToBeUpdated = new User(null, null, "jane@smith.com", null, null);
        String newEmail = userToBeUpdated.getEmail();
        User existingUser = userService.addUser(newUser);
        String existingEmail = existingUser.getEmail();

        User updatedUser = userService.updateUserByEmail(existingEmail, userToBeUpdated);

        assertNotNull(updatedUser, "Updated user should not be null");
        assertEquals(updatedUser.getEmail(), newEmail, "Emails should match");
        // assert unchanged fields
        assertEquals("John", updatedUser.getFirstName(), "First names should match");
        assertEquals("Doe", updatedUser.getLastName(), "Last names should match");
        assertEquals("password", updatedUser.getPassword(), "Passwords should match");
        assertNull(updatedUser.getMyTodos());

        Optional<User> notFoundUser = userService.findUserByEmail(existingEmail);
        assertFalse(notFoundUser.isPresent(), "User with old email should not exist after update");

        Optional<User> foundUser = userService.findUserByEmail(newEmail);
        assertTrue(foundUser.isPresent(), "User with new email should exist after update");
        assertEquals(updatedUser, foundUser.get(), "Updated user should match the found user");

        userService.deleteUserByEmail(updatedUser.getEmail());
    }

    @Test
    void testUpdateFirstAndLastName_Success() {
        User userToBeUpdated = new User("Jane", "Smith", null, null, null);
        String newFirstName = userToBeUpdated.getFirstName();
        String newLastName = userToBeUpdated.getLastName();
        User existingUser = userService.addUser(newUser);
        String existingEmail = existingUser.getEmail();

        User updatedUser = userService.updateUserByEmail(existingEmail, userToBeUpdated);

        assertNotNull(updatedUser, "Updated user should not be null");
        assertEquals(newFirstName, updatedUser.getFirstName(), "First names should match");
        assertEquals(newLastName, updatedUser.getLastName(), "Last names should match");

        // assert unchanged fields
        assertEquals("password", updatedUser.getPassword(), "Passwords should match");
        assertEquals(existingEmail, updatedUser.getEmail(), "Email should match");
        assertNull(updatedUser.getMyTodos());

        Optional<User> foundUser = userService.findUserByEmail(existingEmail);
        assertTrue(foundUser.isPresent(), "User not found after update");
        assertEquals(updatedUser, foundUser.get(), "Updated user should match the found user");

        userService.deleteUserByEmail(updatedUser.getEmail());
    }

    @Test
    void testUpdateUserPassword_Success() {
        User userToBeUpdated = new User(null, null, null, "newPassword", null);
        String newPassword = userToBeUpdated.getPassword();
        User existingUser = userService.addUser(newUser);
        String existingEmail = existingUser.getEmail();

        User updatedUser = userService.updateUserByEmail(existingEmail, userToBeUpdated);

        assertNotNull(updatedUser, "Updated user should not be null");
        assertEquals(newPassword, updatedUser.getPassword(), "Passwords should match");
        // assert unchanged fields
        assertEquals("John", updatedUser.getFirstName(), "First names should match");
        assertEquals("Doe", updatedUser.getLastName(), "Last names should match");
        assertEquals(existingEmail, updatedUser.getEmail(), "Email should match");
        assertNull(updatedUser.getMyTodos());

        Optional<User> foundUser = userService.findUserByEmail(existingEmail);
        assertTrue(foundUser.isPresent(), "User not found after update");
        assertEquals(updatedUser, foundUser.get(), "Updated user should match the found user");

        userService.deleteUserByEmail(updatedUser.getEmail());
    }

    @Test
    void testUpdateUserFail_UserIsNull() {
        User existingUser = userService.addUser(newUser);
        String existingEmail = existingUser.getEmail();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.updateUserByEmail(existingEmail, null));

        assertEquals("User cannot be null", exception.getMessage());

        userService.deleteUserByEmail(existingEmail);
    }

    @Test
    void testUpdateUserFail_NoChangesMade() {
        User userToBeUpdated = new User();
        User existingUser = userService.addUser(newUser);
        String existingEmail = existingUser.getEmail();

        NoChangesMadeException exception = assertThrows(NoChangesMadeException.class, () -> userService.updateUserByEmail(existingEmail, userToBeUpdated));

        assertEquals("No changes have been made.", exception.getMessage());

        userService.deleteUserByEmail(existingEmail);
    }

    @Test
    void testUpdateUserFail_InvalidEmailFormat() {
        User userToBeUpdated = new User(null, null, "invalid@email", null, null);
        User existingUser = userService.addUser(newUser);
        String existingEmail = existingUser.getEmail();

        InvalidEmailFormatException exception = assertThrows(InvalidEmailFormatException.class, () -> userService.updateUserByEmail(existingEmail, userToBeUpdated));

        assertEquals("Email must be in the format 'example@domain.com' and contain only letters.", exception.getMessage());

        Optional<User> foundUser = userService.findUserByEmail(existingEmail);
        assertTrue(foundUser.isPresent(), "User not found after update");

        userService.deleteUserByEmail(existingEmail);
    }

    @Test
    void testUpdateUserFail_InvalidNameFormat() {
        User userToBeUpdated = new User("inv@lid", null, null, null, null);
        User existingUser = userService.addUser(newUser);
        String existingEmail = existingUser.getEmail();

        InvalidNameFormatException exception = assertThrows(InvalidNameFormatException.class, () -> userService.updateUserByEmail(existingEmail, userToBeUpdated));

        assertEquals("Name must contain at least 2 letters.", exception.getMessage());

        Optional<User> foundUser = userService.findUserByEmail(existingEmail);
        assertTrue(foundUser.isPresent(), "User not found after update");
        assertNotEquals("inv@lid", foundUser.get().getFirstName(), "Old name should not be changed");

        userService.deleteUserByEmail(existingEmail);
    }

    @Test
    void testUpdateUserFail_InvalidPassword() {
        User userToBeUpdated = new User(null, null, null, "short", null);
        User existingUser = userService.addUser(newUser);
        String existingEmail = existingUser.getEmail();

        InvalidPasswordFormatException exception = assertThrows(InvalidPasswordFormatException.class, () -> userService.updateUserByEmail(existingEmail, userToBeUpdated));

        assertEquals("Password must be at least 8 characters long and contain only letters and/or digits.", exception.getMessage());

        Optional<User> foundUser = userService.findUserByEmail(existingEmail);
        assertTrue(foundUser.isPresent(), "User not found after update");
        assertNotEquals("short", foundUser.get().getPassword(), "Password should not be changed");

        userService.deleteUserByEmail(existingEmail);
    }

    @Test
    void testDeleteUserByEmail_Success() {
        User existingUser = userService.addUser(newUser);
        String existingEmail = existingUser.getEmail();

        userService.deleteUserByEmail(existingEmail);

        assertTrue(userService.getAll().isEmpty());
    }

    @Test
    void testDeleteUserByEmail_UserNotFound() {
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userService.deleteUserByEmail("john@doe.com"));
        assertEquals("User with email john@doe.com not found", exception.getMessage());
    }

    @Test
    void testDeleteUserByEmail_EmailIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.deleteUserByEmail(null));
        assertEquals("Email cannot be null", exception.getMessage());
    }

    @Test
    public void testGetAll_WhenListIsEmpty() {
        List<User> users = userService.getAll();

        assertNotNull(users, "Returned list should not be null");
        assertTrue(users.isEmpty(), "Returned list should be empty");
    }

    @Test
    public void testGetAll_WhenListHasUsers() {
        User user1 = newUser;
        User user2 = new User("Jane", "Smith", "jane@smith.com", "password", null);

        userService.addUser(user1);
        userService.addUser(user2);

        List<User> users = userService.getAll();

        assertNotNull(users, "Returned list should not be null");
        assertEquals(2, users.size(), "List should contain 2 users");
        assertTrue(users.contains(user1), "List should contain user1");
        assertTrue(users.contains(user2), "List should contain user2");

        users.clear();
    }
}
