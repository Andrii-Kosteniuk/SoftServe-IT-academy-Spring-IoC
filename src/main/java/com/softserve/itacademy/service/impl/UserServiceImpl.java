package com.softserve.itacademy.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.softserve.itacademy.exception.user.*;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.UserService;
import static com.softserve.itacademy.validation.UserValidator.*;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final List<User> users;

    public UserServiceImpl() {
        users = new ArrayList<>();
    }

    @Override
    public User addUser(User user) {
        requireNonNull(user, "User cannot be null");
        String email = user.getEmail();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String password = user.getPassword();

        requireNonNull(email, "Email cannot be null");
        requireNonNull(firstName, "First name cannot be null");
        requireNonNull(lastName, "Last name cannot be null");
        requireNonNull(password, "Password cannot be null");

        if (!isValidEmail(email)) throw new InvalidEmailFormatException();
        if (findUserByEmail(email).isPresent()) throw new EmailAlreadyExistsException(email);
        if (!isValidFirstName(firstName) || !isValidLastName(lastName)) throw new InvalidNameFormatException();
        if (!isValidPassword(password)) throw new InvalidPasswordFormatException();

        users.add(user);
        LOGGER.info("Successfully added user with email: {}", email);
        return user;
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return users.stream().
                filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public User updateUserByEmail(String email, User updatedUser) {
        requireNonNull(updatedUser, "User cannot be null");
        requireNonNull(email, "Email cannot be null");
        User existingUser = findUserByEmail(email).orElseThrow(() -> new UserNotFoundException(email));

        String newEmail = updatedUser.getEmail();
        String newFirstName = updatedUser.getFirstName();
        String newLastName = updatedUser.getLastName();
        String newPassword = updatedUser.getPassword();
        boolean updated = false;

        updated |= updateEmail(newEmail, existingUser);
        updated |= updateFirstName(newFirstName, existingUser);
        updated |= updateLastName(newLastName, existingUser);
        updated |= updatePassword(newPassword, existingUser);

        if (!updated) throw new NoChangesMadeException();
        LOGGER.info("Successfully updated user with email: {}", existingUser.getEmail());
        return existingUser;
    }

    @Override
    public void deleteUserByEmail(String email) {
        requireNonNull(email, "Email cannot be null");

        if (!users.removeIf(user -> user.getEmail().equals(email))) {
            LOGGER.info("User was not found, deletion failed.");
            throw new UserNotFoundException(email);
        }
        LOGGER.info("Successfully deleted user with email: {}", email);
    }

    @Override
    public List<User> getAll() {
        return users;
    }

    private boolean updateEmail(String newEmail, User existingUser) {
        if (newEmail != null && !existingUser.getEmail().equals(newEmail)) {
            if (!isValidEmail(newEmail)) {
                throw new InvalidEmailFormatException();
            }
            existingUser.setEmail(newEmail);
            return true;
        }
        return false;
    }

    private boolean updateFirstName(String newName, User existingUser) {
        if (newName != null && !existingUser.getFirstName().equals(newName)) {
            if (!isValidFirstName(newName)) {
                throw new InvalidNameFormatException();
            }
            existingUser.setFirstName(newName);
            return true;
        }
        return false;
    }

    private boolean updateLastName(String newName, User existingUser) {
        if (newName != null && !existingUser.getLastName().equals(newName)) {
            if (!isValidLastName(newName)) {
                throw new InvalidNameFormatException();
            }
            existingUser.setLastName(newName);
            return true;
        }
        return false;
    }

    private boolean updatePassword(String newPassword, User existingUser) {
        if (newPassword != null && !existingUser.getPassword().equals(newPassword)) {
            if (!isValidPassword(newPassword)) {
                throw new InvalidPasswordFormatException();
            }
            existingUser.setPassword(newPassword);
            return true;
        }
        return false;
    }

    private void requireNonNull(Object field, String message) {
        if (field == null) {
            throw new IllegalArgumentException(message);
        }
    }
}
