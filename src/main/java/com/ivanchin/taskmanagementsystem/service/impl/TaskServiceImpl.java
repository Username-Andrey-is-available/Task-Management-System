package com.ivanchin.taskmanagementsystem.service.impl;

import com.ivanchin.taskmanagementsystem.dto.TaskDTO;
import com.ivanchin.taskmanagementsystem.exception.UnauthorizedAccessException;
import com.ivanchin.taskmanagementsystem.exception.UnauthorizedTaskStatusChangeException;
import com.ivanchin.taskmanagementsystem.model.Task;
import com.ivanchin.taskmanagementsystem.model.TaskPriority;
import com.ivanchin.taskmanagementsystem.model.TaskStatus;
import com.ivanchin.taskmanagementsystem.model.User;
import com.ivanchin.taskmanagementsystem.repository.TaskRepository;
import com.ivanchin.taskmanagementsystem.repository.UserRepository;
import com.ivanchin.taskmanagementsystem.service.TaskService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Optional<Task> getTaskById(Long taskId) {
        return taskRepository.findById(taskId);
    }

    @Override
    public Task createTask(TaskDTO taskDTO, UserDetails userDetails) {
        Task task = new Task();
        task.setAuthor(userRepository.findByName(userDetails.getUsername()).orElseThrow());
        task.setAssignee(userRepository.findByName(taskDTO.getAssignee()).orElseThrow());
        if (taskDTO.getStatus() != null) {
            task.setStatus(TaskStatus.valueOf(taskDTO.getStatus()));
        }

        if (taskDTO.getDescription() != null) {
            task.setDescription(taskDTO.getDescription());
        }

        if (taskDTO.getPriority() != null) {
            task.setPriority(TaskPriority.valueOf(taskDTO.getPriority()));
        }

        if (taskDTO.getTitle() != null) {
            task.setTitle(taskDTO.getTitle());
        }
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Long taskId, TaskDTO taskDTO, UserDetails userDetails) {
        Task task = taskRepository.findById(taskId).orElseThrow(() ->
                new EntityNotFoundException("Task not found"));
        User user = userRepository.findByName(userDetails.getUsername()).orElseThrow(() ->
                new EntityNotFoundException("User not found"));

        if (task.getAuthor().getId() != user.getId()) {
            throw new UnauthorizedTaskStatusChangeException("User does not have permission to update the task");
        } else {
            try {
                if (taskDTO.getPriority() != null) {
                    task.setPriority(TaskPriority.valueOf(taskDTO.getPriority()));
                }
                if (taskDTO.getDescription() != null) {
                    task.setDescription(taskDTO.getDescription());
                }
                if (taskDTO.getTitle() != null) {
                    task.setTitle(taskDTO.getTitle());
                }
                if (taskDTO.getAssignee() != null) {
                    task.setAssignee(userRepository.findByName(taskDTO.getAssignee()).orElseThrow());
                }

                return taskRepository.save(task);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid task update");
            }
        }
    }


    @Override
    public List<Task> getAllTasksFrom(String email) {
        return userRepository.findByEmail(email)
                .map(user -> taskRepository.findByAuthorId(user.getId()))
                .orElse(Collections.emptyList());
    }

    @Override
    public List<Task> getAllTasksFor(String email) {
        return userRepository.findByEmail(email)
                .map(user -> taskRepository.findByAssigneeId(user.getId()))
                .orElse(Collections.emptyList());
    }

    @Override
    public void deleteTask(Long taskId, UserDetails userDetails) {
        User user = userRepository.findByName(userDetails.getUsername()).
                orElseThrow();
        if (user.getRole().equals("ROLE_ADMIN") ||
                taskRepository.findById(taskId).orElseThrow().
                        getAuthor().getId() == user.getId()) {
            taskRepository.deleteById(taskId);
        } else {
            throw new UnauthorizedAccessException
                    ("User is not the author of the task and does not have the right to delete it");
        }
    }

    @Override
    public Task changeStatus(Long taskId, TaskDTO taskDTO, UserDetails userDetails) {
        Task task = taskRepository.findById(taskId).orElseThrow(() ->
                new EntityNotFoundException("Task not found"));
        User user = userRepository.findByName(userDetails.getUsername()).orElseThrow(() ->
                new EntityNotFoundException("User not found"));

        if (task.getAssignee().getId() != user.getId()) {
            throw new UnauthorizedTaskStatusChangeException
                    ("User does not have permission to change the task status");
        } else {
            try {
                task.setStatus(TaskStatus.valueOf(taskDTO.getStatus()));
                return taskRepository.save(task);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid task status");
            }
        }
    }
}
