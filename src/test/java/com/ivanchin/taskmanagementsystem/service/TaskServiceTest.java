package com.ivanchin.taskmanagementsystem.service;

import com.ivanchin.taskmanagementsystem.model.Task;
import com.ivanchin.taskmanagementsystem.repository.TaskRepository;
import com.ivanchin.taskmanagementsystem.repository.UserRepository;
import com.ivanchin.taskmanagementsystem.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    void getAllTasks_ShouldReturnListOfTasks() {
        // Arrange
        when(taskRepository.findAll()).thenReturn(Collections.singletonList(new Task()));

        // Act
        var result = taskService.getAllTasks();

        // Assert
        assertEquals(1, result.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void getTaskById_WithExistingTaskId_ShouldReturnTask() {
        // Arrange
        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(new Task()));

        // Act
        var result = taskService.getTaskById(taskId);

        // Assert
        assertTrue(result.isPresent());
        verify(taskRepository, times(1)).findById(taskId);
    }

    @Test
    void getTaskById_WithNonExistingTaskId_ShouldReturnEmptyOptional() {
        // Arrange
        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // Act
        var result = taskService.getTaskById(taskId);

        // Assert
        assertTrue(result.isEmpty());
        verify(taskRepository, times(1)).findById(taskId);
    }

    // Add more test cases for other methods as needed
}
