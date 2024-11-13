package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.exception.todo.ToDoException;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.ToDoService;
import com.softserve.itacademy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ToDoServiceImpl implements ToDoService {

    private final UserService userService;

    @Autowired
    public ToDoServiceImpl(UserService userService) {
        this.userService = userService;
    }

    private static void checkToDoIsNotNull(ToDo todo) {
        if (todo == null) throw new IllegalArgumentException("To-do can not be null");
    }

    public ToDo addTodo(ToDo givenToDo, User givenUser) {
        if (givenToDo == null || givenUser == null) {
            throw new IllegalArgumentException("You provide incorrect data");
        }

        // Here I check whether to-do 'title' and 'createdAt' are correct or not.
        checkIfTodoIsCorrect(givenToDo);


        // Here I find a user and add to-do to his toDoList that is called "myTodos" and return this to-do
        Optional<User> user = userService.getAll().stream()
                .filter(u -> u.getFirstName().equals(givenUser.getFirstName()))
                .findFirst();

        if (user.isPresent()) {
            if (user.get().getMyTodos() == null) {
                user.get().setMyTodos(new ArrayList<>());
            }
            user.get().getMyTodos().add(givenToDo);
            return givenToDo;
        }
        throw new IllegalArgumentException("User " + givenUser + "  not found");
    }

    // I use comparison by date createdAt like unique id for to-do item to avoid updating not appropriate to-do item
    public ToDo updateTodo(ToDo todo) {
        checkToDoIsNotNull(todo);
        checkIfTodoIsCorrect(todo);

        // Here I find the to-do which I want to update. Then I set it to toDoList
        for (User user : userService.getAll()) {
            List<ToDo> todos = user.getMyTodos();
            for (int i = 0; i < todos.size(); i++) {
                ToDo toDo = todos.get(i);
                if (toDo.getCreatedAt().equals(todo.getCreatedAt())) {
                    todos.set(i, todo);
                    return todo;
                }
            }
        }
        return null;
    }

    // I use comparison by date createdAt like unique id for to-do item to avoid updating not appropriate to-do item
    public void deleteTodo(ToDo todo) {
        checkToDoIsNotNull(todo);

        userService.getAll().stream()
                .filter(user -> user.getMyTodos().stream()
                        .anyMatch(toDo -> toDo.getCreatedAt().equals(todo.getCreatedAt())))
                .findFirst()
                .ifPresent(user -> user.getMyTodos().removeIf(toDo -> toDo.getTitle().equals(todo.getTitle())));
    }

    public List<ToDo> getAll() {
        return userService.getAll()
                .stream()
                .flatMap(user -> Optional.ofNullable(user.getMyTodos()).orElse(Collections.emptyList()).stream())
                .collect(Collectors.toList());
    }

    public List<ToDo> getByUser(User user) {
        if (user == null) throw new IllegalArgumentException("User can not be null");

        List<User> allUsers = userService.getAll();
        return allUsers.stream()
                .filter(u -> u.equals(user))
                .flatMap(t -> t.getMyTodos().stream())
                .collect(Collectors.toList());

    }

    public ToDo getByUserTitle(User givenUser, String givenTitle) {
        if (givenUser == null || givenTitle == null) {
            throw new IllegalArgumentException("You provide incorrect data");
        }

        return userService.getAll()
                .stream()
                .filter(user -> user.equals(givenUser))
                .flatMap(user -> user.getMyTodos().stream())
                .filter(toDo -> toDo.getTitle().equals(givenTitle))
                .findFirst().orElse(null);
    }

    public void checkIfTodoIsCorrect(ToDo toDo) {
        if (toDo.getTitle().isEmpty()
            || toDo.getCreatedAt().isAfter(LocalDateTime.now())) {
            throw new ToDoException("Something went wrong!!! Todo has incorrect data");
        }
    }

}