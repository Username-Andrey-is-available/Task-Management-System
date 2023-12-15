package com.ivanchin.taskmanagementsystem.controller;

import com.ivanchin.taskmanagementsystem.dto.TaskDTO;
import com.ivanchin.taskmanagementsystem.model.Task;
import com.ivanchin.taskmanagementsystem.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;


    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long taskId) {
        Optional<Task> task = taskService.getTaskById(taskId);
        return task.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/for/{email}")
    public ResponseEntity<?> getTaskByAssignee(@PathVariable String email) {
        List<Task> tasks = taskService.getAllTasksFor(email);
        if (tasks != null && !tasks.isEmpty()) {
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No tasks found for assignee: " + email, HttpStatus.OK);
        }
    }

    @GetMapping("/from/{email}")
    public ResponseEntity<?> getTaskByAuthor(@PathVariable String email) {
        List<Task> tasks = taskService.getAllTasksFrom(email);
        if (tasks != null && !tasks.isEmpty()) {
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No tasks found from author: " + email, HttpStatus.OK);
        }
    }


    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task createdTask = taskService.createTask(task);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable Long taskId, @RequestBody TaskDTO taskDTO) {
        Task updatedTask = taskService.updateTask(taskId, taskDTO);
        if (updatedTask != null) {
            return new ResponseEntity<>(updatedTask, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
