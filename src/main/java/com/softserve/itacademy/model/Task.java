package com.softserve.itacademy.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Task {

    private String name;

    private Priority priority;

    public Task(String oldTask) {
        this.name = oldTask;
    }
}
