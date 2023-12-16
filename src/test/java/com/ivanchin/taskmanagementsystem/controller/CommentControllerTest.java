package com.ivanchin.taskmanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivanchin.taskmanagementsystem.dto.CommentDTO;
import com.ivanchin.taskmanagementsystem.model.Comment;
import com.ivanchin.taskmanagementsystem.service.CommentService;
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
import static org.mockito.Mockito.*;

class CommentControllerTest {

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentController commentController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllComments_WithAdminRole_ShouldReturnListOfComments() {
        // Arrange
        when(commentService.getAllComments()).thenReturn(Arrays.asList(new Comment(), new Comment()));

        // Act
        ResponseEntity<List<Comment>> response = commentController.getAllComments();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void getCommentById_WithValidId_ShouldReturnComment() {
        // Arrange
        Long commentId = 1L;
        Comment comment = new Comment();
        when(commentService.getCommentById(commentId)).thenReturn(Optional.of(comment));

        // Act
        ResponseEntity<Comment> response = commentController.getCommentById(commentId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(comment, response.getBody());
    }

    @Test
    void getCommentById_WithInvalidId_ShouldReturnNotFound() {
        // Arrange
        Long commentId = 1L;
        when(commentService.getCommentById(commentId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Comment> response = commentController.getCommentById(commentId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void createComment_WithValidInput_ShouldReturnCreated() throws Exception {
        // Arrange
        Long taskId = 1L;
        CommentDTO commentDTO = new CommentDTO();
        UserDetails userDetails = new User("username", "password", Arrays.asList());

        when(commentService.createComment(eq(taskId), any(CommentDTO.class), eq(userDetails)))
                .thenReturn(new Comment());

        // Act
        ResponseEntity<Comment> response = commentController.createComment(taskId, commentDTO, userDetails);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void updateComment_WithValidInput_ShouldReturnUpdatedComment() {
        // Arrange
        Long commentId = 1L;
        String newText = "Updated Text";
        UserDetails userDetails = new User("username", "password", Arrays.asList());

        when(commentService.updateComment(eq(commentId), eq(newText), eq(userDetails)))
                .thenReturn(new Comment());

        // Act
        ResponseEntity<Comment> response = commentController.updateComment(commentId, newText, userDetails);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void updateComment_WithInvalidId_ShouldReturnNotFound() {
        // Arrange
        Long commentId = 1L;
        String newText = "Updated Text";
        UserDetails userDetails = new User("username", "password", Arrays.asList());

        when(commentService.updateComment(eq(commentId), eq(newText), eq(userDetails)))
                .thenReturn(null);

        // Act
        ResponseEntity<Comment> response = commentController.updateComment(commentId, newText, userDetails);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteComment_WithValidId_ShouldReturnNoContent() {
        // Arrange
        Long commentId = 1L;

        // Act
        ResponseEntity<Void> response = commentController.deleteComment(commentId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(commentService, times(1)).deleteComment(commentId);
    }
}
