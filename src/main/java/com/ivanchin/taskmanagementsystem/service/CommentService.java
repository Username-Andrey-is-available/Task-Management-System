package com.ivanchin.taskmanagementsystem.service;

import com.ivanchin.taskmanagementsystem.dto.CommentDTO;
import com.ivanchin.taskmanagementsystem.model.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

@Schema(description = "Интерфейс сервиса для работы с комментариями.")
public interface CommentService {

    @Schema(description = "Получение списка всех комментариев.")
    List<Comment> getAllComments();

    @Schema(description = "Получение комментария по его уникальному идентификатору.")
    Optional<Comment> getCommentById(Long commentId);

    @Schema(description = "Создание нового комментария для задачи.")
    Comment createComment(
            @Schema(description = "Уникальный идентификатор задачи, к которой относится комментарий.")
            Long taskId,
            @Schema(description = "DTO-объект с информацией о новом комментарии.")
            CommentDTO commentDTO,
            @Schema(description = "Информация о пользователе, создающем комментарий.")
            UserDetails userDetails
    );

    @Schema(description = "Обновление текста комментария.")
    Comment updateComment(
            @Schema(description = "Уникальный идентификатор комментария, который требуется обновить.")
            Long commentId,
            @Schema(description = "Новый текст для комментария.")
            String newText,
            @Schema(description = "Информация о пользователе, обновляющем комментарий.")
            UserDetails userDetails
    );

    @Schema(description = "Удаление комментария.")
    void deleteComment(
            @Schema(description = "Уникальный идентификатор комментария, который требуется удалить.")
            Long commentId
    );
}
