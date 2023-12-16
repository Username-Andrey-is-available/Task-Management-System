package com.ivanchin.taskmanagementsystem.service;

import com.ivanchin.taskmanagementsystem.config.SecurityUserDetails;
import com.ivanchin.taskmanagementsystem.dto.CommentDTO;
import com.ivanchin.taskmanagementsystem.exception.UnauthorizedTaskStatusChangeException;
import com.ivanchin.taskmanagementsystem.model.Comment;
import com.ivanchin.taskmanagementsystem.model.Task;
import com.ivanchin.taskmanagementsystem.model.User;
import com.ivanchin.taskmanagementsystem.repository.CommentRepository;
import com.ivanchin.taskmanagementsystem.repository.TaskRepository;
import com.ivanchin.taskmanagementsystem.repository.UserRepository;
import com.ivanchin.taskmanagementsystem.service.impl.CommentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllComments_ShouldReturnListOfComments() {
        // Arrange
        List<Comment> comments = new ArrayList<>();
        when(commentRepository.findAll()).thenReturn(comments);

        // Act
        List<Comment> result = commentService.getAllComments();

        // Assert
        assertEquals(comments, result);
    }

    @Test
    void getCommentById_WithValidId_ShouldReturnComment() {
        // Arrange
        Long commentId = 1L;
        Comment comment = new Comment();
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        // Act
        Optional<Comment> result = commentService.getCommentById(commentId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(comment, result.get());
    }

    @Test
    void getCommentById_WithInvalidId_ShouldReturnEmptyOptional() {
        // Arrange
        Long commentId = 1L;
        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());

        // Act
        Optional<Comment> result = commentService.getCommentById(commentId);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    @Transactional
    void createComment_WithValidInput_ShouldReturnCreatedComment() {
        // Arrange
        Long taskId = 1L;
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setText("Test Comment");
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setRole("ROLE_USER");
        user.setName("Test User");

        Task task = new Task();
        task.setId(taskId);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(userRepository.findByName(anyString())).thenReturn(Optional.of(user));
        when(commentRepository.save(any(Comment.class))).thenAnswer(invocation -> {
            Comment savedComment = invocation.getArgument(0);
            savedComment.setId(1L);
            return savedComment;
        });

        // Act
        Comment result = commentService.createComment(taskId, commentDTO, new SecurityUserDetails(user));

        // Assert
        assertNotNull(result);
        assertEquals(commentDTO.getText(), result.getText());
        assertEquals(user, result.getUser());
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    void updateComment_WithValidInputAndAuthorizedUser_ShouldReturnUpdatedComment() {
        // Arrange
        Long commentId = 1L;
        String newText = "Updated Text";
        User user = new User();
        user.setId(1L);
        user.setName("Tinker");
        Comment existingComment = new Comment();
        existingComment.setUser(user);

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(existingComment));
        when(userRepository.findByName("Tinker")).thenReturn(Optional.of(user));
        when(commentRepository.save(any(Comment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Comment result = commentService.updateComment(commentId, newText, new SecurityUserDetails(user));

        // Assert
        assertNotNull(result);
        assertEquals(newText, result.getText());
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    void updateComment_WithValidInputAndUnauthorizedUser_ShouldThrowException() {
        // Arrange
        Long commentId = 1L;
        String newText = "Updated Text";
        User user = new User();
        user.setId(2L);
        user.setName("Pudge");
        User user2 = new User();
        user2.setId(3L);
        Comment existingComment = new Comment();
        existingComment.setUser(user2);

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(existingComment));
        when(userRepository.findByName("Pudge")).thenReturn(Optional.of(user));

        // Act & Assert
        assertThrows(UnauthorizedTaskStatusChangeException.class,
                () -> commentService.updateComment(commentId, newText, new SecurityUserDetails(user)));
    }

    @Test
    void deleteComment_WithValidId_ShouldDeleteComment() {
        // Arrange
        Long commentId = 1L;

        // Act
        commentService.deleteComment(commentId);

        // Assert
        verify(commentRepository, times(1)).deleteById(commentId);
    }
}
