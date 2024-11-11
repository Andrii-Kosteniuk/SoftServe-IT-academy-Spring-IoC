package com.softserve.itacademy;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(JUnitPlatform.class)
public class UserServiceTest {

    private static UserService userService;
    private User newUser;

    @BeforeAll
    public static void setupBeforeClass() throws Exception {
        AnnotationConfigApplicationContext annotationConfigContext = new AnnotationConfigApplicationContext(Config.class);
        userService = annotationConfigContext.getBean(UserService.class);
        annotationConfigContext.close();
    }

    @BeforeEach
    void setUpUser() {
        newUser = new User();
        newUser.setFirstName("John");
        newUser.setLastName("Doe");
        newUser.setEmail("john@doe.com");
        newUser.setPassword("password");
    }

    @Test
    public void testAddUser_Success() {
        User expectedUser = new User("John", "Doe",
                "john@doe.com", "password", null);

        User actualUser = userService.addUser(newUser);

        assertEquals(expectedUser, actualUser, "Users should be equal");

        Optional<User> foundUser = userService.findUserByEmail(actualUser.getEmail());
        assertTrue(foundUser.isPresent(), "User not found");
        assertEquals(expectedUser, foundUser.get(), "Users should be equal");
    }
}
