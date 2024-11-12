package com.softserve.itacademy.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Data
@NoArgsConstructor
public class ToDo {

    private String title;

    private LocalDateTime createdAt;

    private User owner;

    private List<Task> tasks;

    private ToDo(ToDoBuilder builder) {
        this.title = builder.title;
        this.createdAt = builder.createdAt;
        this.owner = builder.owner;
        this.tasks = builder.tasks;
    }


    public static class ToDoBuilder {
        private String title;
        private LocalDateTime createdAt;
        private User owner;
        private List<Task> tasks;

        public ToDoBuilder() {

        }

        public ToDoBuilder title(String title) {
            this.title = title;
            return this;
        }

        public ToDoBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ToDoBuilder owner(User owner) {
            this.owner = owner;
            return this;
        }

        public ToDoBuilder tasks(List<Task> tasks) {
            this.tasks = tasks;
            return this;
        }

        public ToDo build() {
            return new ToDo(this);
        }
    }

}
