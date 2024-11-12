package com.softserve.itacademy;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.impl.UserServiceImpl;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigContext = new AnnotationConfigApplicationContext(Config.class);

        UserServiceImpl userService = annotationConfigContext.getBean(UserServiceImpl.class);

        System.out.println("=== Adding Users ===");
        User user1 = new User("John", "Doe", "john@doe.com", "password", null);
        User user2 = new User("Jane", "Smith", "jane@smith.com", "password", null);
        User user3 = new User("Joe", "Braun", "joe@braun.com", "password", null);

        userService.addUser(user1);
        System.out.println("Added: " + user1);
        userService.addUser(user2);
        System.out.println("Added: " + user2);
        userService.addUser(user3);
        System.out.println("Added: " + user3);

        System.out.println("\n=== All Users After Adding ===");
        userService.getAll().forEach(System.out::println);

        System.out.println("\n=== Updating Users ===");
        userService.updateUserByEmail(user1.getEmail(), new User(null, null, null, "newPassword123", null));
        System.out.println("Updated password for: " + user1.getEmail());

        userService.updateUserByEmail(user2.getEmail(), new User(null, null, "jane.smith123@example.com", null, null));
        System.out.println("Updated email for: " + user2.getEmail());

        userService.updateUserByEmail(user3.getEmail(), new User("Jessica", "White", "jessica@white.com", null, null));
        System.out.println("Updated name and email for: " + user3.getEmail());

        System.out.println("\n=== All Users After Updates ===");
        userService.getAll().forEach(System.out::println);

        System.out.println("\n=== Deleting Users ===");
        userService.deleteUserByEmail(user1.getEmail());
        System.out.println("Deleted: " + user1.getEmail());

        userService.deleteUserByEmail(user2.getEmail());
        System.out.println("Deleted: " + user2.getEmail());

        userService.deleteUserByEmail(user3.getEmail());
        System.out.println("Deleted: " + user3.getEmail());

        System.out.println("\n=== All Users After Deletions ===");
        userService.getAll().forEach(System.out::println);
    }
}
