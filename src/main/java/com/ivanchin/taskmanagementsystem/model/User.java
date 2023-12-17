package com.ivanchin.taskmanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
@Schema(description = "Сущность, представляющая информацию о пользователе в системе.")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Уникальный идентификатор пользователя.")
    private Long id;

    @Column(unique = true)
    @Schema(description = "Уникальный адрес электронной почты пользователя.")
    private String email;

    @Schema(description = "Пароль пользователя.")
    private String password;

    @Schema(description = "Роль пользователя в системе.")
    private String role;

    @Column(unique = true)
    @Schema(description = "Имя пользователя.")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "author")
    @Schema(description = "Список задач, созданных пользователем.")
    private List<Task> authoredTasks;

    @JsonIgnore
    @OneToMany(mappedBy = "assignee")
    @Schema(description = "Список задач, назначенных пользователю.")
    private List<Task> assignedTasks;
}
