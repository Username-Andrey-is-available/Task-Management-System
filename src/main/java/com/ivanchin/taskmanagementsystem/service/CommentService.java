package com.ivanchin.taskmanagementsystem.service;

import com.ivanchin.taskmanagementsystem.dto.CommentDTO;
import com.ivanchin.taskmanagementsystem.model.Comment;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    List<Comment> getAllComments();

    Optional<Comment> getCommentById(Long commentId);

    Comment createComment(Long taskId, CommentDTO commentDTO, UserDetails userDetails);

    Comment updateComment(Long commentId, String newText, UserDetails userDetails);

    void deleteComment(Long commentId);
}
