package com.softserve.itacademy.service;

import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;

import java.util.List;

/**
 * Service interface for managing ToDo.
 */
public interface ToDoService {

    /**
     * Adds a new ToDo item for a given user.
     *
     * @param todo the ToDo item to add
     * @param user the owner of the ToDo item
     * @return the saved ToDo item
     */
    ToDo addTodo(ToDo todo, User user);


    /**
     * Updates an existing ToDo item.
     *
     * @param todo the ToDo item with updated information
     * @return the updated ToDo item
     */
    ToDo updateTodo(ToDo todo);


    /**
     * Deletes a ToDo item.
     *
     * @param todo the ToDo item to delete
     */
    void deleteTodo(ToDo todo);


    /**
     * Retrieves all ToDo items.
     *
     * @return a list of all ToDo items
     */
    List<ToDo> getAll();


    /**
     * Retrieves all ToDo items for a given user.
     *
     * @param user the user whose ToDo items to retrieve
     * @return a list of ToDo items owned by the user
     */
    List<ToDo> getByUser(User user);


    /**
     * Retrieves a ToDo item for a given user by title.
     *
     * @param user the owner of the ToDo item
     * @param title the title of the ToDo item
     * @return the matching ToDo item, or null if not found
     */
    ToDo getByUserTitle(User user, String title);

}