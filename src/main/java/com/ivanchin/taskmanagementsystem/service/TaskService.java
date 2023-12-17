package com.ivanchin.taskmanagementsystem.service;

import com.ivanchin.taskmanagementsystem.dto.TaskDTO;
import com.ivanchin.taskmanagementsystem.model.Task;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

@Schema(description = "Интерфейс сервиса для работы с задачами.")
public interface TaskService {

    @Schema(description = "Получение списка всех задач.")
    List<Task> getAllTasks();

    @Schema(description = "Получение задачи по ее уникальному идентификатору.")
    Optional<Task> getTaskById(
            @Schema(description = "Уникальный идентификатор задачи.")
            Long taskId
    );

    @Schema(description = "Создание новой задачи.")
    Task createTask(
            @Schema(description = "DTO-объект с информацией о новой задаче.")
            TaskDTO taskDTO,
            @Schema(description = "Информация о пользователе, создающем задачу.")
            UserDetails userDetails
    );

    @Schema(description = "Обновление задачи.")
    Task updateTask(
            @Schema(description = "Уникальный идентификатор задачи, которую требуется обновить.")
            Long taskId,
            @Schema(description = "DTO-объект с информацией для обновления задачи.")
            TaskDTO taskDTO,
            @Schema(description = "Информация о пользователе, обновляющем задачу.")
            UserDetails userDetails
    );

    @Schema(description = "Удаление задачи.")
    void deleteTask(
            @Schema(description = "Уникальный идентификатор задачи, которую требуется удалить.")
            Long taskId,
            @Schema(description = "Информация о пользователе, удаляющем задачу.")
            UserDetails userDetails
    );

    @Schema(description = "Получение списка всех задач, созданных пользователем с указанным email.")
    List<Task> getAllTasksFrom(
            @Schema(description = "Email пользователя, задачи которого требуется получить.")
            String email
    );

    @Schema(description = "Получение списка всех задач, назначенных пользователю с указанным email.")
    List<Task> getAllTasksFor(
            @Schema(description = "Email пользователя, задачи которого требуется получить.")
            String email
    );

    @Schema(description = "Изменение статуса задачи.")
    Task changeStatus(
            @Schema(description = "Уникальный идентификатор задачи, статус которой требуется изменить.")
            Long taskId,
            @Schema(description = "DTO-объект с информацией для изменения статуса задачи.")
            TaskDTO taskDTO,
            @Schema(description = "Информация о пользователе, изменяющем статус задачи.")
            UserDetails userDetails
    );
}

