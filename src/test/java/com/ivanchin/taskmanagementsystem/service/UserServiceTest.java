package com.ivanchin.taskmanagementsystem.service;

import com.ivanchin.taskmanagementsystem.model.User;
import com.ivanchin.taskmanagementsystem.repository.UserRepository;
import com.ivanchin.taskmanagementsystem.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getAllUsers_ShouldReturnListOfUsers() {
        // Arrange
        when(userRepository.findAll()).thenReturn(Collections.singletonList(new User()));

        // Act
        var result = userService.getAllUsers();

        // Assert
        assertEquals(1, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserById_WithExistingUserId_ShouldReturnUser() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));

        // Act
        var result = userService.getUserById(userId);

        // Assert
        assertNotNull(result);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void getUserById_WithNonExistingUserId_ShouldReturnNull() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act
        var result = userService.getUserById(userId);

        // Assert
        assertNull(result);
        verify(userRepository, times(1)).findById(userId);
    }

    // Add more test cases for other methods as needed
}
