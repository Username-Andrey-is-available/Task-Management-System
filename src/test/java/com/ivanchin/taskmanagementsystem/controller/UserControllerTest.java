package com.ivanchin.taskmanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivanchin.taskmanagementsystem.dto.UserDTO;
import com.ivanchin.taskmanagementsystem.model.User;
import com.ivanchin.taskmanagementsystem.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers_WithAdminRole_ShouldReturnListOfUsers() {
        // Arrange
        when(userService.getAllUsers()).thenReturn(Arrays.asList(new User(), new User()));

        // Act
        ResponseEntity<List<User>> response = userController.getAllUsers();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void getUserById_WithValidId_ShouldReturnUser() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        when(userService.getUserById(userId)).thenReturn(user);

        // Act
        ResponseEntity<User> response = userController.getUserById(userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void getUserById_WithInvalidId_ShouldReturnNotFound() {
        // Arrange
        Long userId = 1L;
        when(userService.getUserById(userId)).thenReturn(null);

        // Act
        ResponseEntity<User> response = userController.getUserById(userId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getUserByName_WithValidName_ShouldReturnUser() {
        // Arrange
        String userName = "testUser";
        User user = new User();
        when(userService.getUserByName(userName)).thenReturn(user);

        // Act
        ResponseEntity<User> response = userController.getUserByName(userName);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void getUserByName_WithInvalidName_ShouldReturnNotFound() {
        // Arrange
        String userName = "nonexistentUser";
        when(userService.getUserByName(userName)).thenReturn(null);

        // Act
        ResponseEntity<User> response = userController.getUserByName(userName);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void createUser_WithValidInput_ShouldReturnCreated() throws Exception {
        // Arrange
        UserDTO userDTO = new UserDTO();
        when(userService.createUser(any(UserDTO.class))).thenReturn(new User());

        // Act
        ResponseEntity<User> response = userController.createUser(userDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void updateUser_WithValidInput_ShouldReturnUpdatedUser() {
        // Arrange
        Long userId = 1L;
        UserDTO userDTO = new UserDTO();
        when(userService.updateUser(eq(userId), any(UserDTO.class))).thenReturn(new User());

        // Act
        ResponseEntity<User> response = userController.updateUser(userId, userDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void updateUser_WithInvalidId_ShouldReturnNotFound() {
        // Arrange
        Long userId = 1L;
        UserDTO userDTO = new UserDTO();
        when(userService.updateUser(eq(userId), any(UserDTO.class))).thenReturn(null);

        // Act
        ResponseEntity<User> response = userController.updateUser(userId, userDTO);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteUser_WithValidId_ShouldReturnNoContent() {
        // Arrange
        Long userId = 1L;

        // Act
        ResponseEntity<Void> response = userController.deleteUser(userId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).deleteUser(userId);
    }
}
