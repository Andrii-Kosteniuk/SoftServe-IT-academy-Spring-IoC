package com.softserve.itacademy.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


import com.softserve.itacademy.exception.task.InvalidPriorityFormatException;
import com.softserve.itacademy.exception.task.NameAlreadyExistsException;
import com.softserve.itacademy.exception.task.TaskNotFoundException;
import com.softserve.itacademy.exception.user.InvalidNameFormatException;
import com.softserve.itacademy.exception.user.NoChangesMadeException;
import com.softserve.itacademy.exception.user.UserNotFoundException;
import com.softserve.itacademy.model.Priority;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.TaskService;
import com.softserve.itacademy.service.ToDoService;

import static com.softserve.itacademy.validation.TaskValidator.isValidName;
import static com.softserve.itacademy.validation.TaskValidator.isValidPriority;
import static java.util.Objects.requireNonNull;

@Service
public class TaskServiceImpl implements TaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private ToDoService toDoService;
    private List<Task> tasks = new ArrayList<>();

    @Autowired
    public TaskServiceImpl(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    public Task addTask(Task task, ToDo todo) {
        requireNonNull(task, "Task cannot be null");

        String name = task.getName();
        Priority priority = task.getPriority();

        requireNonNull(name, "Name cannot be null");
        requireNonNull(priority, "Priority name cannot be null");

        if (!isValidName(name)) throw new InvalidNameFormatException();
        if (findTaskByName(name).isPresent()) throw new NameAlreadyExistsException(name);
        if (!isValidPriority(priority)) throw new InvalidPriorityFormatException(priority);

        toDoService.updateTodo(todo);
        tasks.add(task);
        LOGGER.info("Successfully added task with name: {}", name);
        return task;
    }

    public Task updateTask(String name, Task task) {
        requireNonNull(task, "Task cannot be null");
        requireNonNull(name, "Name cannot be null");
        Task existingTask = findTaskByName(name).orElseThrow(() -> new UserNotFoundException(name));

        String newName = task.getName();
        Priority newPriority = task.getPriority();
        boolean updated = false;

        updated |= updateName(newName, existingTask);
        updated |= updatePriority(newPriority, existingTask);

        if (!updated) throw new NoChangesMadeException();
        LOGGER.info("Successfully updated task with name: {}", task.getName());
        return existingTask;
    }

    public void deleteTaskByName(String name) {
        requireNonNull(name, "Name cannot be null");

        if (!tasks.removeIf(user -> user.getName().equals(name))) {
            LOGGER.info("Task was not found, deletion failed.");
            throw new TaskNotFoundException(name);
        }
        LOGGER.info("Successfully deleted task with name: {}", name);
    }

    @Override
    public Optional<Task> findTaskByName(String name) {
        return tasks.stream().
                filter(task -> task.getName().equals(name))
                .findFirst();
    }

    public List<Task> getAll() {
        return tasks;
    }

    public List<Task> getByToDo(ToDo todo) {
        if (todo == null) {
            return Collections.emptyList();
        }
        return todo.getTasks();
    }

    public Task getByToDoName(ToDo todo, String name) {
        if (todo == null || name == null || name.isEmpty())
            return null;

        for (Task task : todo.getTasks())
            if (task.getName().equalsIgnoreCase(name))
                return task;

        return null;
    }

    public Task getByUserName(User user, String name) {
        if (user == null || name == null || name.isEmpty())
            return null;

        for (ToDo todo : user.getMyTodos())
            for (Task task : todo.getTasks())
                if (task.getName().equalsIgnoreCase(name))
                    return task;

        return null;
    }

    private boolean updateName(String newName, Task existingTask) {
        if (newName != null && !existingTask.getName().equals(newName)) {
            if (!isValidName(newName)) {
                throw new InvalidNameFormatException();
            }
            existingTask.setName(newName);
            return true;
        }
        return false;
    }

    private boolean updatePriority(Priority newPriority, Task existingTask) {
        if (newPriority != null && !existingTask.getPriority().equals(newPriority)) {
            if (!isValidPriority(newPriority)) {
                throw new InvalidNameFormatException();
            }
            existingTask.setPriority(newPriority);
            return true;
        }
        return false;
    }

}
