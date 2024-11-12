package com.softserve.itacademy;


import com.softserve.itacademy.model.Priority;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.ToDoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ToDoServiceTest {
    List<ToDo> toDosForUser_1;
    List<ToDo> toDosForUser_2;
    List<Task> tasks;
    @Mock
    private ToDoService toDoService;
    private User user_1;
    private User user_2;


    @BeforeEach
    void setUp() {

        toDosForUser_1 = new ArrayList<>();
        toDosForUser_2 = new ArrayList<>();

        tasks = Arrays.asList(
                new Task("Implement Todo class", Priority.HIGH),
                new Task("Implement ToDoServiceIml class", Priority.HIGH));

        user_1 = new User("Andrii", "Kosteniuk", "andrii@gmail.com", "andriiPassword", toDosForUser_1);
        user_2 = new User("Alex", "Last", "alex@gmail.com", "alexPassword", toDosForUser_2);

        ToDo toDo_1 = new ToDo.ToDoBuilder()
                .title("My first to-do")
                .createdAt(LocalDateTime.of(2024, Month.NOVEMBER, 11, 10, 0))
                .owner(user_1)
                .tasks(tasks)
                .build();

        ToDo toDo_2 = new ToDo.ToDoBuilder()
                .title("My second to-do")
                .createdAt(LocalDateTime.of(2024, Month.NOVEMBER, 12, 17, 0))
                .owner(user_2)
                .tasks(tasks)
                .build();
        toDosForUser_1.add(toDo_1);
        toDosForUser_2.add(toDo_2);
    }

    @Test
    @DisplayName("Check if new toDo is assigned to given user")

    public void checkAddToDo() {
        ToDo newTodo = new ToDo.ToDoBuilder()
                .title("My new to-do")
                .createdAt(LocalDateTime.of(2024, Month.NOVEMBER, 11, 17, 1))
                .owner(user_1)
                .tasks(tasks)
                .build();

        //Arrange:
        when(toDoService.addTodo(newTodo, user_1)).thenReturn(newTodo);

        // Act:
        ToDo actualToDo = toDoService.addTodo(newTodo, user_1);

        // Assert:
        assertEquals(newTodo, actualToDo);
        verify(toDoService, times(1)).addTodo(newTodo, user_1);
    }

    @Test
    @DisplayName("Check if toDo is updated")

    public void checkUpdateTodo() {
        ToDo updatedToDo = new ToDo.ToDoBuilder()
                .title("My new to-do")
                .createdAt(LocalDateTime.of(2024, Month.NOVEMBER, 12, 20, 1))
                .owner(user_2)
                .tasks(tasks)
                .build();

        // Arrange:
        when(toDoService.updateTodo(updatedToDo)).thenReturn(updatedToDo);

        // Act:
        ToDo actualToDo = toDoService.updateTodo(updatedToDo);

        // Assert:
        assertEquals(updatedToDo, actualToDo);
        assertEquals(updatedToDo.getTitle(), actualToDo.getTitle());
        assertEquals(updatedToDo.getCreatedAt().toLocalDate(), actualToDo.getCreatedAt().toLocalDate());
        assertEquals(actualToDo.getOwner().getFirstName(), "Alex");

        verify(toDoService, times(1)).updateTodo(updatedToDo);

    }

    @Test
    @DisplayName("Check if toDo is deleted")
    public void checkDeleteToDo() {

        ToDo deletedTodo = toDosForUser_1.get(0);

        // Act:
        toDoService.deleteTodo(deletedTodo);

        //Assert:
        verify(toDoService, times(1)).deleteTodo(deletedTodo);

    }

    @Test
    @DisplayName("Check if method return all todos")
    public void checkAllTodoAreReturnedProperly() {
        // Arrange:
        when(toDoService.getAll()).thenReturn(toDosForUser_1);

        // Act:
        List<ToDo> allToDo = toDoService.getAll();

        //Assert:
        assertEquals(1, allToDo.size(), "The list of todos does not contain all todos");
        assertEquals(allToDo.get(0).getTitle(), "My first to-do");

        verify(toDoService, times(1)).getAll();

    }

    @Test
    @DisplayName("Check if method return list of todos for given user")
    public void checkGetByUser() {
        // Arrange:
        when(toDoService.getByUser(user_2)).thenReturn(toDosForUser_2);

        // Act:
        List<ToDo> allToDoByUser = toDoService.getByUser(user_2);

        //Assert:
        assertEquals(1, allToDoByUser.size(), "The list of todos does not contain all todos");
        assertEquals(allToDoByUser.get(0).getTitle(), "My second to-do");

        verify(toDoService, times(1)).getByUser(user_2);

    }

    @Test
    @DisplayName("Check if method return todo by user and todo title")
    public void checkByUserTitle() {
        String title = "My first to-do";
        // Arrange:
        ToDo toDo = user_1.getMyTodos().get(0);
        when(toDoService.getByUserTitle(user_1, title)).thenReturn(toDo);

        // Act:
        ToDo toDoByUserTitle = toDoService.getByUserTitle(user_1, title);

        //Assert:
        assertEquals(toDo, toDoByUserTitle, "The todo does not match");

        verify(toDoService, times(1)).getByUserTitle(user_1, title);

    }


}
