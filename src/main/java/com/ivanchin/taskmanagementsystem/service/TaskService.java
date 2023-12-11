package com.ivanchin.taskmanagementsystem.service;

import com.ivanchin.taskmanagementsystem.dto.TaskUpdateDTO;
import com.ivanchin.taskmanagementsystem.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<Task> getAllTasks();
    List<Task> getTasksByAuthorId(Long authorId);
    List<Task> getTasksByAssigneeId(Long assigneeId);
    Optional<Task> getTaskById(Long taskId);
    Task createTask(Task task);
    Task updateTask(Long taskId, TaskUpdateDTO taskUpdateDto);
    void deleteTask(Long taskId);
    void changeTaskStatus(Long taskId, String status);
    void assignTask(Long taskId, Long assigneeId);
}
