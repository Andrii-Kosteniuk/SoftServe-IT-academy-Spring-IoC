package com.softserve.itacademy.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.softserve.itacademy.exception.user.*;
import org.springframework.stereotype.Service;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.UserService;
import static com.softserve.itacademy.validation.UserValidator.*;

@Service
public class UserServiceImpl implements UserService {

    private List<User> users;

    public UserServiceImpl() {
        users = new ArrayList<>();
    }

    @Override
    public User addUser(User user) {
        if (user == null) throw new IllegalArgumentException("User cannot be null");
        String email = user.getEmail();

        if (!isValidEmail(email)) throw new InvalidEmailFormatException();

        if (findUserByEmail(email).isPresent()) throw new EmailAlreadyExistsException(email);

        if (!isValidUserName(user.getFirstName(), user.getLastName())) throw new InvalidNameFormatException();

        if (isValidPassword(user.getPassword())) throw new InvalidPasswordFormatException();

        users.add(user);
        return user;
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return users.stream().
                filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public User updateUser(User user) {
        // TODO
        return null;
    }

    @Override
    public void deleteUser(User user) {
        // TODO
    }

    @Override
    public List<User> getAll() {
        // TODO
        return null;
    }

}
