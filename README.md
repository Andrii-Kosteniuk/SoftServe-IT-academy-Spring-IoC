## ‘ToDo List’ Application

Please, create Service Layer of ‘ToDo List’ Application should be able to:

1. Add new user to the Application.
2. Update/Delete/Gets existing users.
3. Add new **To-Do List** to an existing user.
4. Update/Delete/Gets existing 'To-Do Lists'.
5. Add new **Task** to an existing 'To-Do List'.
6. Update/Delete/Gets existing 'Tasks'.

Write tests for all the above operations.

Implement all necessary getters and setters for model level classes.

You should run tests to demonstrate the execution of the service level code.

\* Save a list of all users as a field in the UserService class.

## Introduction

This project demonstrates the implementation of the Service Layer for a `To-Do List` application using **Spring IoC (
Inversion of Control)**. It provides functionality to manage users, their to-do lists, and tasks associated with these
lists. The Service Layer includes comprehensive CRUD (Create, Read, Update, Delete) operations and is tested using unit
tests to verify functionality.

---

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Setup and Installation](#setup-and-installation)
- [Implementation Details](#implementation-details)
- [Testing](#testing)

---

## Features

### User Management

- Add new users.
- Update, delete, or read existing users.

### To-Do List Management

- Add new to-do lists for existing users.
- Update, delete, or read existing to-do lists.

### Task Management

- Add new tasks to existing to-do lists.
- Update, delete, or read existing tasks.

### Service Layer Tests

- Comprehensive tests for all CRUD operations:
    - User operations
    - To-Do List operations
    - Task operations

---

## Technologies Used

- **Spring Framework**: Dependency Injection (IoC) and Bean Management.
- **JUnit / TestNG**: For writing and running tests.
- **Maven/Gradle**: Build automation and dependency management.
- **Java**: Core programming language.

---

## Setup and Installation

1. **Clone the Repository**
   ```bash
   git clone https://github.com/Andrii-Kosteniuk/SoftServe-IT-academy-Spring-IoC.git

2. Build the Project

- Use Maven:

    ```bash
  mvn clean install

- Or Gradle:
    ```bash
  gradle build

3. Run Tests:
    ```bash
   mvn test

## **Implementation Details**

**Structure**<br>
The application follows a layered architecture:

1. **Model:** Contains classes representing **User**, **ToDoList**, and **Task** entities with appropriate fields,
   getters, and setters.

    - **User:**
        - ```firstName```: First name of the user.
        - ```lastName```: Last name of the user.
        - ```email```: User's email.
        - ```password```: User's password.
        - ```myTodos```: List of associated to-do lists.
    - **ToDo:**
        - ```title```: Title of the to-do list.
        - ```createdAt```: Date when the todo was created.
        - ```owner```: Owner of this todo.
        - ```tasks```: List of associated tasks.
    - **Task:**
        - ```name```: Task name.
        - ```priority```: Priority level (e.g., Low, Medium, High).
2. **Service Layer:**

    - **UserService:**
        - Manages users and their associated to-do lists.
        - Stores a list of all users as a field for persistence during runtime.
    - **ToDoListService:**
        - Handles operations related to to-do lists.
    - **TaskService:**
      Handles operations related to tasks.
3. Tests:
    - Test classes validate the functionality of service classes using mock data.

## Testing

- Test Framework: JUnit  
Run the tests using the command:
 ```bash
mvn test
    