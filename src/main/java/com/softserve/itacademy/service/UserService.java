package com.softserve.itacademy.service;

import java.util.List;
import java.util.Optional;

import com.softserve.itacademy.model.User;

public interface UserService {
    
    User addUser(User user);

    Optional<User> findUserByEmail(String email);

    User updateUser(User user);

    void deleteUser(User user);

    List<User> getAll();

}
