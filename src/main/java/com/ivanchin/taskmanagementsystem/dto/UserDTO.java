package com.ivanchin.taskmanagementsystem.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class UserDTO {
    private String email;
    private String password;
    private String name;
}
