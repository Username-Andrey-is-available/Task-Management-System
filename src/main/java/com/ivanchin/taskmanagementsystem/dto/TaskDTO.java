package com.ivanchin.taskmanagementsystem.dto;

import lombok.Data;

@Data
public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    private String status;
    private String priority;
    private UserDTO author;
    private UserDTO assignee;
}
