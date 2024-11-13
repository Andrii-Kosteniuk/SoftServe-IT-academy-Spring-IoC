package com.softserve.itacademy;

import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.impl.ToDoServiceImpl;
import com.softserve.itacademy.service.impl.UserServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigContext = new AnnotationConfigApplicationContext(Config.class);

        UserServiceImpl userService = annotationConfigContext.getBean(UserServiceImpl.class);
        ToDoServiceImpl toDoService = annotationConfigContext.getBean(ToDoServiceImpl.class);

        System.out.println("USER_SERVICE: === Adding Users ===");
        User user1 = new User("John", "Doe", "john@doe.com", "password", null);
        User user2 = new User("Jane", "Smith", "jane@smith.com", "password", null);
        User user3 = new User("Joe", "Braun", "joe@braun.com", "password", null);

        userService.addUser(user1);
        System.out.println("USER_SERVICE: Added: " + user1);
        userService.addUser(user2);
        System.out.println("USER_SERVICE: Added: " + user2);
        userService.addUser(user3);
        System.out.println("USER_SERVICE: Added: " + user3);

        System.out.println("\nUSER_SERVICE: === All Users After Adding ===");
        userService.getAll().forEach(user -> System.out.println("USER_SERVICE: " + user));

        System.out.println("\nUSER_SERVICE: === Updating Users ===");
        userService.updateUserByEmail(user1.getEmail(), new User(null, null, null, "newPassword123", null));
        System.out.println("USER_SERVICE: Updated password for: " + user1.getEmail());

        userService.updateUserByEmail(user2.getEmail(), new User(null, null, "jane.smith123@example.com", null, null));
        System.out.println("USER_SERVICE: Updated email for: " + user2.getEmail());

        userService.updateUserByEmail(user3.getEmail(), new User("Jessica", "White", "jessica@white.com", null, null));
        System.out.println("USER_SERVICE: Updated name and email for: " + user3.getEmail());

        System.out.println("\nUSER_SERVICE: === All Users After Updates ===");
        userService.getAll().forEach(user -> System.out.println("USER_SERVICE: " + user));



        System.out.println("\n ========================================================");

        System.out.print("           +++++ TodoService implementation +++++");

        System.out.println("\n ========================================================");


        ToDo todo_1 = new ToDo.ToDoBuilder()
                .title("Todo-1")
                .createdAt(LocalDateTime.of(2024, Month.NOVEMBER, 11, 17, 15))
                .owner(user1)
                .tasks(new ArrayList<>())
                .build();
        ToDo todo_2 = new ToDo.ToDoBuilder()
                .title("Todo-2")
                .createdAt(LocalDateTime.of(2024, Month.OCTOBER, 5, 15, 10))
                .owner(user1)
                .tasks(new ArrayList<>())
                .build();
        ToDo todo_3 = new ToDo.ToDoBuilder()
                .title("Todo-3")
                .createdAt(LocalDateTime.of(2024, Month.NOVEMBER, 8, 12, 1))
                .owner(user1)
                .tasks(new ArrayList<>())
                .build();

        System.out.println(" \n");
        System.out.println("====> Assigning todo to specific user <====");

        toDoService.addTodo(todo_1, user1);
        System.out.println("ToDo: " + todo_1 + " was added to " + user1 + " todo list");

        toDoService.addTodo(todo_2, user2);
        System.out.println("ToDo: " + todo_2 + " was added to " + user2 + " todo list");

        toDoService.addTodo(todo_3, user3);
        System.out.println("ToDo: " + todo_3 + " was added to " + user2 + " todo list \n");

        System.out.println("====> Retrieving all existing todo  <====");

        printAllToDo(toDoService.getAll());

        System.out.println("\n ====> Updating todo  <====");
        ToDo todo_3_updated = new ToDo.ToDoBuilder()
                .title("Todo-3-updated")
                .createdAt(LocalDateTime.of(2024, Month.NOVEMBER, 8, 12, 1))
                .owner(user1)
                .tasks(new ArrayList<>())
                .build();
        toDoService.updateTodo(todo_3_updated);
        System.out.println("Todo " + todo_3 + " has been updated");
        System.out.println("Now it has new title:  [" + toDoService.getAll().get(2).getTitle() + "] \n");

        System.out.println("====> Deletion todo  <====");
        toDoService.deleteTodo(todo_2);
        System.out.println("Todo " + todo_2.getTitle() + " was deleted from user's todo list");
        System.out.println();
        System.out.println("Now we have only " + toDoService.getAll().size() + " todos");

        printAllToDo(toDoService.getAll());

        System.out.println("\n ====> Getting todo by user <====");

        User gievenUser = userService.getAll().get(0);
        List<ToDo> toDosByUser = toDoService.getByUser(gievenUser);

        System.out.println("Todos by user " + gievenUser.getFirstName() + "--> " + toDosByUser + " \n");


        System.out.println("\n ====> Getting todo by user and title <====");

        User givenUser1 = userService.getAll().get(0);
        User givenUser2 = userService.getAll().get(1);
        ToDo byUserTitle1 = toDoService.getByUserTitle(givenUser1, "Todo-1");
        ToDo byUserTitle2 = toDoService.getByUserTitle(givenUser2, "Todo-2");

        System.out.println("Todos by user and title is such --> " + byUserTitle1 + " \n");

        System.out.println("Previously we have deleted a todo with title \"Todo-2\" so that we get a null \n");
        System.out.println("Todos by user and title is such --> " + byUserTitle2 + " \n");

        System.out.println("\nUSER_SERVICE: === Deleting Users ===");
        userService.deleteUserByEmail(user1.getEmail());
        System.out.println("USER_SERVICE: Deleted: " + user1.getEmail());

        userService.deleteUserByEmail(user2.getEmail());
        System.out.println("USER_SERVICE: Deleted: " + user2.getEmail());

        userService.deleteUserByEmail(user3.getEmail());
        System.out.println("USER_SERVICE: Deleted: " + user3.getEmail());

        System.out.println("\nUSER_SERVICE: === All Users After Deletions ===");
        if (userService.getAll().isEmpty()) {
            System.out.println("USER_SERVICE: User list is empty");
        } else {
            userService.getAll().forEach(user -> System.out.println("USER_SERVICE: " + user));
        }

        annotationConfigContext.close();
    }

    private static void printAllToDo(List<ToDo> allTodo) {
        allTodo.forEach(toDo -> System.out.print("[" + toDo.getTitle() + "] \n"));
    }
}
