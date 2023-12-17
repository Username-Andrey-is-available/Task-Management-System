package com.ivanchin.taskmanagementsystem.service;

import com.ivanchin.taskmanagementsystem.dto.UserDTO;
import com.ivanchin.taskmanagementsystem.model.User;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Интерфейс сервиса для работы с пользователями.")
public interface UserService {

    @Schema(description = "Получение списка всех пользователей.")
    List<User> getAllUsers();

    @Schema(description = "Получение пользователя по его уникальному идентификатору.")
    User getUserById(
            @Schema(description = "Уникальный идентификатор пользователя.")
            Long userId
    );

    @Schema(description = "Создание нового пользователя.")
    User createUser(
            @Schema(description = "DTO-объект с информацией о новом пользователе.")
            UserDTO userDto
    );

    @Schema(description = "Обновление информации о пользователе.")
    User updateUser(
            @Schema(description = "Уникальный идентификатор пользователя, которого требуется обновить.")
            Long userId,
            @Schema(description = "DTO-объект с информацией для обновления пользователя.")
            UserDTO userDTO
    );

    @Schema(description = "Удаление пользователя.")
    void deleteUser(
            @Schema(description = "Уникальный идентификатор пользователя, которого требуется удалить.")
            Long userId
    );

    @Schema(description = "Получение пользователя по его имени.")
    User getUserByName(
            @Schema(description = "Имя пользователя.")
            String userName
    );
}

