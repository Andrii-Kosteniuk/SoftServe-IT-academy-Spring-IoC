package com.softserve.itacademy.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@EqualsAndHashCode
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

    public List<Task> getTasks() {
        if (tasks == null) tasks = new ArrayList<>();
        return tasks;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ToDo{");
        sb.append(", title='").append(title).append('\'');
        sb.append(", owner=").append(owner.getFirstName()).append(owner.getLastName());
        sb.append("createdAt=").append(createdAt);
        sb.append(", tasks=").append(tasks);
        sb.append('}');
        return sb.toString();
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