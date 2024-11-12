package com.softserve.itacademy;

import com.softserve.itacademy.Config;
import com.softserve.itacademy.model.Priority;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.TaskService;
import com.softserve.itacademy.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(JUnitPlatform.class)
public class TaskServiceTest {

    private static UserService userService;
    private static TaskService taskService;
    private User user;
    private ToDo todo;
    private Task task;

    @BeforeAll
    public static void setupBeforeClass() {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(Config.class);
        taskService = context.getBean(TaskService.class);
        context.close();
    }

    @BeforeEach
    public void setup() {
        task = new Task("Task", Priority.HIGH);

        user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@gmail.com");
        user.setPassword("password");

        todo = new ToDo();
        todo.setTitle("MyToDo");
        todo.setCreatedAt(LocalDateTime.now());
        todo.setOwner(user);

        user.getMyTodos().add(todo);
    }

    @Test
    @DisplayName("Test Get All Tasks from ToDo")
    public void checkGetByToDo() {
        Task task1 = new Task("Task 1");
        Task task2 = new Task("Task 2");
        todo.getTasks().add(task1);
        todo.getTasks().add(task2);

        List<Task> tasks = taskService.getByToDo(todo);
        assertEquals(2, tasks.size());
        assertTrue(tasks.contains(task1));
        assertTrue(tasks.contains(task2));
    }

    @Test
    @DisplayName("Test Get Task by Name from ToDo")
    public void checkGetByToDoName() {
        todo.getTasks().add(task);
        Task result = taskService.getByToDoName(todo, "Task");
        assertNotNull(result, "Task should be found by name in ToDo");
        assertEquals("Task", result.getName());
    }

    @Test
    @DisplayName("Test Get Task by Name from User's ToDos")
    public void checkGetByUserName() {
        ToDo anotherToDo = new ToDo();
        Task anotherTask = new Task("Search Task");
        anotherToDo.getTasks().add(anotherTask);
        user.getMyTodos().add(anotherToDo);

        Task result = taskService.getByUserName(user, "Search Task");
        assertNotNull(result, "Task should be found in user's ToDo lists");
        assertEquals("Search Task", result.getName());
    }
}
