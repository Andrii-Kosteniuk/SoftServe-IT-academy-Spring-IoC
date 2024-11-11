package com.softserve.itacademy.service;

import java.util.List;

import com.softserve.itacademy.model.User;

public interface UserService {
    
    User addUser(User user);

    User findUserByEmail(String email);

    User updateUser(User user);

    void deleteUser(User user);

    List<User> getAll();

}
