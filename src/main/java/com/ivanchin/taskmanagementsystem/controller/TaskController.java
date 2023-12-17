package com.ivanchin.taskmanagementsystem.controller;

import com.ivanchin.taskmanagementsystem.dto.TaskDTO;
import com.ivanchin.taskmanagementsystem.exception.UnauthorizedAccessException;
import com.ivanchin.taskmanagementsystem.model.Task;
import com.ivanchin.taskmanagementsystem.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Задачи", description = "Операции для управления задачами")
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @Operation(
            summary = "Получить все задачи, для админа"
    )
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @Operation(
            summary = "Получить задачу по айди"
    )
    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long taskId) {
        return taskService.getTaskById(taskId).map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(
            summary = "Получить задачи по исполнителю"
    )
    @GetMapping("/for/{email}")
    public ResponseEntity<?> getTaskByAssignee(@PathVariable String email) {
        List<Task> tasks = taskService.getAllTasksFor(email);
        if (tasks != null && !tasks.isEmpty()) {
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No tasks found for assignee: " + email, HttpStatus.OK);
        }
    }

    @Operation(
            summary = "Получить задачи по автору"
    )
    @GetMapping("/from/{email}")
    public ResponseEntity<?> getTaskByAuthor(@PathVariable String email) {
        List<Task> tasks = taskService.getAllTasksFrom(email);
        if (tasks != null && !tasks.isEmpty()) {
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No tasks found from author: " + email, HttpStatus.OK);
        }
    }

    @Operation(
            summary = "Создать задачу"
    )
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody TaskDTO taskDTO,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        try {
            if (taskDTO == null || userDetails == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(
                    taskService.createTask(taskDTO, userDetails),
                    HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Обновить задачу, для автора"
    )
    @PatchMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable Long taskId, @RequestBody TaskDTO taskDTO,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        try {
            if (taskDTO == null || userDetails == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(
                    taskService.updateTask(taskId, taskDTO, userDetails),
                    HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Изменить статус задачи, для исполнителя"
    )
    @PutMapping("/{taskId}")
    public ResponseEntity<Task> changeStatus(@PathVariable long taskId, @RequestBody TaskDTO taskDTO,
                                             @AuthenticationPrincipal UserDetails userDetails) {
        try {
            if (taskDTO == null || userDetails == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(
                    taskService.changeStatus(taskId, taskDTO, userDetails),
                    HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Удалить задачу"
    )
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        try {
            taskService.deleteTask(taskId, userDetails);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (UnauthorizedAccessException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}

