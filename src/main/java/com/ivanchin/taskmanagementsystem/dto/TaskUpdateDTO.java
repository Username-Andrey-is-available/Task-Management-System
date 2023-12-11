package com.ivanchin.taskmanagementsystem.dto;

import com.ivanchin.taskmanagementsystem.model.User;
import lombok.Data;

@Data
public class TaskUpdateDTO {
    private String status;
    private String priority;
    private User author;
    private User assignee;
}

