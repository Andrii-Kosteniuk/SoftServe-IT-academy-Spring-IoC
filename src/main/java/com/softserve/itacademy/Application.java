package com.softserve.itacademy;

import com.softserve.itacademy.model.Priority;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.impl.TaskServiceImpl;
import com.softserve.itacademy.service.impl.ToDoServiceImpl;
import com.softserve.itacademy.service.impl.UserServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigContext = new AnnotationConfigApplicationContext(Config.class);

        UserServiceImpl userService = annotationConfigContext.getBean(UserServiceImpl.class);
        ToDoServiceImpl toDoService = annotationConfigContext.getBean(ToDoServiceImpl.class);
        TaskServiceImpl taskService = annotationConfigContext.getBean(TaskServiceImpl.class);

        System.out.println("=== ALL SERVICES INITIALIZE DATA ===\n");

        System.out.println("USER_SERVICE: === Adding Users ===");
        User user1 = new User("John", "Doe", "john@doe.com", "password", null);
        User user2 = new User("Jane", "Smith", "jane@smith.com", "password", null);
        User user3 = new User("Joe", "Braun", "joe@braun.com", "password", null);

        System.out.println("TASK_SERVICE: === Adding tasks ===");
        Task task1 = new Task("Task1", Priority.HIGH);
        Task task2 = new Task("Task2", Priority.LOW);
        Task task3 = new Task("Task3", Priority.MEDIUM);

        System.out.println("TODO_SERVICE: === Adding Todo ===\n");
        ToDo todo_1 = new ToDo.ToDoBuilder()
                .title("Todo-1")
                .createdAt(LocalDateTime.of(2024, Month.NOVEMBER, 11, 17, 15))
                .owner(user1)
                .tasks(new ArrayList<>(Arrays.asList(task1)))
                .build();
        ToDo todo_2 = new ToDo.ToDoBuilder()
                .title("Todo-2")
                .createdAt(LocalDateTime.of(2024, Month.OCTOBER, 5, 15, 10))
                .owner(user1)
                .tasks(new ArrayList<>(Arrays.asList(task2)))
                .build();
        ToDo todo_3 = new ToDo.ToDoBuilder()
                .title("Todo-3")
                .createdAt(LocalDateTime.of(2024, Month.NOVEMBER, 8, 12, 1))
                .owner(user1)
                .tasks(new ArrayList<>(Arrays.asList(task3)))
                .build();

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


        System.out.println(" \n");
        System.out.println("TODO_SERVICE: ====> Assigning todo to specific user <====");

        toDoService.addTodo(todo_1, user1);
        System.out.println("TODO_SERVICE: Add" + todo_1 + " to " + user1 + " todo list");

        toDoService.addTodo(todo_2, user2);
        System.out.println("TODO_SERVICE: Add" + todo_2 + " to " + user2 + " todo list");

        toDoService.addTodo(todo_3, user3);
        System.out.println("TODO_SERVICE: Add" + todo_3 + " to " + user2 + " todo list \n");

        System.out.println("TODO_SERVICE: ====> Retrieve all existing todo  <====");

        System.out.println("TODO_SERVICE: print all todo");
        printInfoAboutToDo(toDoService.getAll());

        System.out.println("\nTODO_SERVICE: ====> Do updating todo  <====");

        // Assume we want to update task #3 to provide another title and assign to another user
        System.out.println("TODO_SERVICE: Updating todo title on \"todo_3_updated\"");
        ToDo todo_3_updated = new ToDo.ToDoBuilder()
                .title("Todo-3-updated")
                .createdAt(LocalDateTime.of(2024, Month.NOVEMBER, 8, 12, 1))
                .owner(user1)
                .tasks(new ArrayList<>(Arrays.asList(task3)))
                .build();

        System.out.println("TODO_SERVICE: Updated todo");
        toDoService.updateTodo(todo_3_updated);

        System.out.println("TODO_SERVICE: Todo " + todo_3 + " has been updated");
        System.out.println("TODO_SERVICE: Inform that now it is new title:  [" + toDoService.getAll().get(2).getTitle() + "] \n");

        System.out.println("TODO_SERVICE: ====> Perform deletion todo  <====");
        toDoService.deleteTodo(todo_2);
        System.out.println("TODO_SERVICE: Inform that todo " + todo_2.getTitle() + " was deleted from user's todo list");
        System.out.println();
        System.out.println("TODO_SERVICE: Inform that now we have only " + toDoService.getAll().size() + " todos");

        System.out.println("TODO_SERVICE: Print all todo....");
        printInfoAboutToDo(toDoService.getAll());

        System.out.println("\nTODO_SERVICE: ====> Getting todo by user <====");

        User gievenUser = userService.getAll().get(0);
        List<ToDo> toDosByUser = toDoService.getByUser(gievenUser);

        System.out.println("TODO_SERVICE: Todos by user " + gievenUser.getFirstName() + " are: --> " + " \n");
        printInfoAboutToDo(gievenUser.getMyTodos());

        System.out.println("\nTODO_SERVICE: ====> Getting todo by user and title <====");

        User givenUser1 = userService.getAll().get(0);
        User givenUser2 = userService.getAll().get(1);
        ToDo toDoByUserTitle1 = toDoService.getByUserTitle(givenUser1, "Todo-1");
        ToDo toDoByUserTitle2 = toDoService.getByUserTitle(givenUser2, "Todo-2");

        System.out.println("TODO_SERVICE: Todo by user " + givenUser1.getFirstName() + "and title \"Todo-1\" is such:  --> " + toDoByUserTitle1.getTitle() + " \n");

        System.out.println("Previously we have deleted a todo with title \"Todo-2\" so that we get a null \n");
        System.out.println("TODO_SERVICE: Todo by user " + givenUser2.getFirstName() + " and title \"Todo-2\" is such:  --> " + toDoByUserTitle2 + " \n");


        System.out.println("======> Everything work well\n");

        System.out.println("\n ========================================================");

        System.out.print("           +++++ TaskService implementation +++++");

        System.out.println("\n ========================================================");


        System.out.println("\nTASK_SERVICE: ====> Assigning tasks to specific todo <====");

        taskService.addTask(task1, todo_1);
        System.out.println("TASK_SERVICE: Task: " + task1 + " was added to " + todo_1 + " todo list");

        taskService.addTask(task2, todo_2);
        System.out.println("TASK_SERVICE: Task: " + task2 + " was added to " + todo_2 + " todo list");

        taskService.addTask(task3, todo_3);
        System.out.println("TASK_SERVICE: Task: " + task3 + " was added to " + todo_3 + " todo list");

        System.out.println("\nTASK_SERVICE: ====> Retrieving all existing tasks  <====");

        for (Task task : taskService.getAll())
            System.out.println(task + "\n");

        System.out.println("TASK_SERVICE: ====> Updating task  <====");
        Task task1_updated = new Task("UpdatedTask1", Priority.LOW);
        taskService.updateTask("Task1", task1_updated);
        System.out.println("TASK_SERVICE: Task " + task1 + " has been updated");
        System.out.println("TASK_SERVICE: Now it has new name and priority:  [" + taskService.getAll().get(0).getName()
                           + ", " + taskService.getAll().get(0).getPriority() + "] \n");

        System.out.println("TASK_SERVICE: ====> Deletion task  <====");
        taskService.deleteTaskByName("Task3");
        System.out.println("TASK_SERVICE: Task " + task3.getName() + " was deleted from todo list");
        System.out.println("TASK_SERVICE: Now we have only " + taskService.getAll().size() + " tasks");

        System.out.println("\nTASK_SERVICE: ====> Getting tasks by todo <====");

        List<Task> todoTasks = taskService.getByToDo(todo_1);

        System.out.println("TASK_SERVICE: Task by todo " + todoTasks + "--> " + todo_1 + " \n");


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


    private static void printInfoAboutToDo(List<ToDo> allTodo) {
        allTodo.forEach(toDo -> System.out.print("[Title: " + toDo.getTitle() + "]-[Owner: " + toDo.getOwner().getFirstName() + "]-[ Created at: " + toDo.getCreatedAt() + "] \n"));
    }
}
