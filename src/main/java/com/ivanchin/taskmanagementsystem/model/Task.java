package com.ivanchin.taskmanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "tasks")
@Schema(description = "Сущность, представляющая информацию о задаче в системе.")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Уникальный идентификатор задачи.")
    private Long id;

    @Schema(description = "Заголовок задачи.")
    private String title;

    @Schema(description = "Описание задачи.")
    private String description;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Статус задачи.")
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Приоритет задачи.")
    private TaskPriority priority;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @Schema(description = "Пользователь, создавший задачу.")
    private User author;

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    @Schema(description = "Пользователь, назначенный на выполнение задачи.")
    private User assignee;

    @JsonIgnore
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Список комментариев к задаче.")
    private List<Comment> comments;
}
