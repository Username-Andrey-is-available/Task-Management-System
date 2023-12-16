package com.ivanchin.taskmanagementsystem.service;

import com.ivanchin.taskmanagementsystem.dto.TaskDTO;
import com.ivanchin.taskmanagementsystem.model.Task;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<Task> getAllTasks();

    Optional<Task> getTaskById(Long taskId);

    Task createTask(TaskDTO taskDTO, UserDetails userDetails);

    Task updateTask(Long taskId, TaskDTO taskDTO, UserDetails userDetails);

    void deleteTask(Long taskId, UserDetails userDetails);

    List<Task> getAllTasksFrom(String email);

    List<Task> getAllTasksFor(String email);

    Task changeStatus(Long taskId, TaskDTO taskDTO, UserDetails userDetails);
}
