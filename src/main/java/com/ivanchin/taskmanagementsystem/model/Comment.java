package com.ivanchin.taskmanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "comments")
@Schema(description = "Сущность, представляющая информацию о комментарии в системе.")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Уникальный идентификатор комментария.")
    private Long id;

    @Schema(description = "Текст комментария.")
    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Schema(description = "Пользователь, оставивший комментарий.")
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "task_id")
    @Schema(description = "Задача, к которой относится комментарий.")
    private Task task;
}
