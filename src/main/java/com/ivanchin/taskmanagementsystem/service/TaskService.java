package com.ivanchin.taskmanagementsystem.service;

import com.ivanchin.taskmanagementsystem.dto.TaskDTO;
import com.ivanchin.taskmanagementsystem.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    List<Task> getAllTasks();

    Optional<Task> getTaskById(Long taskId);

    Task createTask(Task task);

    Task updateTask(Long taskId, TaskDTO taskDTO);

    void deleteTask(Long taskId);

    List<Task> getAllTasksFrom(String email);

    List<Task> getAllTasksFor(String email);
}
