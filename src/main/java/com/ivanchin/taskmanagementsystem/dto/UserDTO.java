package com.ivanchin.taskmanagementsystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
@Schema(description = "DTO-объект для передачи данных о пользователе.")
public class UserDTO {

    @Schema(description = "Уникальный идентификатор пользователя.")
    private Long id;

    @Schema(description = "Уникальный адрес электронной почты пользователя.")
    private String email;

    @Schema(description = "Пароль пользователя.")
    private String password;

    @Schema(description = "Имя пользователя.")
    private String name;

    @Schema(description = "Роль пользователя в системе.")
    private String role;
}

