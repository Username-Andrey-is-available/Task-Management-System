package com.ivanchin.taskmanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "author")
    private List<Task> authoredTasks;

    @JsonIgnore
    @OneToMany(mappedBy = "assignee")
    private List<Task> assignedTasks;
}