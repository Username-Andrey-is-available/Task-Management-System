package com.ivanchin.taskmanagementsystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO-объект для передачи данных о задаче.")
public class TaskDTO {

    @Schema(description = "Уникальный идентификатор задачи.")
    private Long id;

    @Schema(description = "Заголовок задачи.")
    private String title;

    @Schema(description = "Описание задачи.")
    private String description;

    @Schema(description = "Статус задачи.")
    private String status;

    @Schema(description = "Приоритет задачи.")
    private String priority;

    @Schema(description = "Назначенный на выполнение задачи пользователь.")
    private String assignee;
}

