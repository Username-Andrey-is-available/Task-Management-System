package com.ivanchin.taskmanagementsystem.dto;

import lombok.Data;


@Data
public class UserDTO {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String role;
}
