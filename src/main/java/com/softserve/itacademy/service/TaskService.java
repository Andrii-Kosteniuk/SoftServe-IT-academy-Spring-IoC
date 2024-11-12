package com.softserve.itacademy.service;

import java.util.List;
import java.util.Optional;

import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;

public interface TaskService {
    
    Task addTask(Task task, ToDo todo);

    Task updateTask(String name, Task task);

    void deleteTaskByName(String name);

    Optional<Task> findTaskByName(String name);

    List<Task> getAll();

    List<Task> getByToDo(ToDo todo);

    Task getByToDoName(ToDo todo, String name);

    Task getByUserName(User user, String name);
    
}
