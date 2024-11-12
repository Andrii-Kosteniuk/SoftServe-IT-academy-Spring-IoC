package com.softserve.itacademy.service;

import java.util.List;
import java.util.Optional;

import com.softserve.itacademy.model.User;

public interface UserService {
    
    User addUser(User user);

    Optional<User> findUserByEmail(String email);

    User updateUserByEmail(String email, User updatedUser);

    void deleteUserByEmail(String email);

    List<User> getAll();

}
