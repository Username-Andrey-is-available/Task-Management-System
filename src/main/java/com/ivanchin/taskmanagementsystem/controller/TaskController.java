package com.ivanchin.taskmanagementsystem.controller;

import com.ivanchin.taskmanagementsystem.dto.TaskDTO;
import com.ivanchin.taskmanagementsystem.dto.TaskUpdateDTO;
import com.ivanchin.taskmanagementsystem.model.Task;
import com.ivanchin.taskmanagementsystem.model.TaskPriority;
import com.ivanchin.taskmanagementsystem.model.TaskStatus;
import com.ivanchin.taskmanagementsystem.model.User;
import com.ivanchin.taskmanagementsystem.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
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

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody TaskDTO taskDTO) {
        Task newTask = new Task();
        newTask.setTitle(taskDTO.getTitle());
        newTask.setDescription(taskDTO.getDescription());

        // Устанавливаем статус и приоритет, если они указаны в DTO
        if (taskDTO.getStatus() != null) {
            newTask.setStatus(TaskStatus.valueOf(taskDTO.getStatus()));
        }

        if (taskDTO.getPriority() != null) {
            newTask.setPriority(TaskPriority.valueOf(taskDTO.getPriority()));
        }

        // Устанавливаем автора и исполнителя, если они указаны в DTO
        if (taskDTO.getAuthor() != null) {
            // Предполагается, что taskDTO.getAuthor() возвращает объект UserDTO
            User author = new User();
            author.setId(taskDTO.getAuthor().getId());
            newTask.setAuthor(author);
        }

        if (taskDTO.getAssignee() != null) {
            // Предполагается, что taskDTO.getAssignee() возвращает объект UserDTO
            User assignee = new User();
            assignee.setId(taskDTO.getAssignee().getId());
            newTask.setAssignee(assignee);
        }

        // Создаем задачу
        Task createdTask = taskService.createTask(newTask);

        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }


    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable Long taskId, @RequestBody TaskUpdateDTO taskUpdateDto) {
        Task updatedTask = taskService.updateTask(taskId, taskUpdateDto);
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
