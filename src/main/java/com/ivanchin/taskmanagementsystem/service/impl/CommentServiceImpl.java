package com.ivanchin.taskmanagementsystem.service.impl;

import com.ivanchin.taskmanagementsystem.dto.CommentDTO;
import com.ivanchin.taskmanagementsystem.exception.UnauthorizedTaskStatusChangeException;
import com.ivanchin.taskmanagementsystem.model.Comment;
import com.ivanchin.taskmanagementsystem.repository.CommentRepository;
import com.ivanchin.taskmanagementsystem.repository.TaskRepository;
import com.ivanchin.taskmanagementsystem.repository.UserRepository;
import com.ivanchin.taskmanagementsystem.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public Optional<Comment> getCommentById(Long commentId) {
        return commentRepository.findById(commentId);
    }

    @Override
    public Comment createComment(Long taskId, CommentDTO commentDTO, UserDetails userDetails) {
        Comment comment = new Comment();
        comment.setTask(taskRepository.findById(taskId).orElseThrow());
        comment.setUser(userRepository.findByName(userDetails.getUsername()).orElseThrow());
        comment.setText(commentDTO.getText());
        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(Long commentId, String newText, UserDetails userDetails) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        if (comment.getUser().getId() != userRepository.findByName(userDetails.getUsername())
                .orElseThrow().getId()) {
            throw new UnauthorizedTaskStatusChangeException
                    ("User does not have permission to update a comment");
        } else {
            comment.setText(newText);
            return commentRepository.save(comment);
        }
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
