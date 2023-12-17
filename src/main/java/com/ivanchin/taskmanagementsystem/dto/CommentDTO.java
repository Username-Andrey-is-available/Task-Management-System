package com.ivanchin.taskmanagementsystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO-объект для передачи данных о комментарии.")
public class CommentDTO {

    @Schema(description = "Уникальный идентификатор комментария.")
    private Long id;

    @Schema(description = "Текст комментария.")
    private String text;

    @Schema(description = "Уникальный идентификатор пользователя, оставившего комментарий.")
    private Long userId;

    @Schema(description = "Уникальный идентификатор задачи, к которой относится комментарий.")
    private Long taskId;
}

