package com.ivanchin.taskmanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivanchin.taskmanagementsystem.dto.TaskDTO;
import com.ivanchin.taskmanagementsystem.model.Task;
import com.ivanchin.taskmanagementsystem.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTasks_WithAdminRole_ShouldReturnListOfTasks() {
        // Arrange
        when(taskService.getAllTasks()).thenReturn(Arrays.asList(new Task(), new Task()));

        // Act
        ResponseEntity<List<Task>> response = taskController.getAllTasks();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void getTaskById_WithValidId_ShouldReturnTask() {
        // Arrange
        Long taskId = 1L;
        Task task = new Task();
        when(taskService.getTaskById(taskId)).thenReturn(Optional.of(task));

        // Act
        ResponseEntity<Task> response = taskController.getTaskById(taskId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(task, response.getBody());
    }

    @Test
    void getTaskById_WithInvalidId_ShouldReturnNotFound() {
        // Arrange
        Long taskId = 1L;
        when(taskService.getTaskById(taskId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Task> response = taskController.getTaskById(taskId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getTaskByAssignee_WithValidEmail_ShouldReturnListOfTasks() {
        // Arrange
        String email = "test@example.com";
        when(taskService.getAllTasksFor(email)).thenReturn(Arrays.asList(new Task(), new Task()));

        // Act
        ResponseEntity<?> response = taskController.getTaskByAssignee(email);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, ((List<?>) response.getBody()).size());
    }

    @Test
    void getTaskByAssignee_WithInvalidEmail_ShouldReturnMessage() {
        // Arrange
        String email = "nonexistent@example.com";
        when(taskService.getAllTasksFor(email)).thenReturn(null);

        // Act
        ResponseEntity<?> response = taskController.getTaskByAssignee(email);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("No tasks found for assignee: " + email, response.getBody());
    }

    @Test
    void createTask_WithValidInput_ShouldReturnCreated() throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO();
        UserDetails userDetails = new User("username", "password", Arrays.asList());

        when(taskService.createTask(any(TaskDTO.class), eq(userDetails))).thenReturn(new Task());

        // Act
        ResponseEntity<Task> response = taskController.createTask(taskDTO, userDetails);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}
