package com.ivanchin.taskmanagementsystem.service;

import com.ivanchin.taskmanagementsystem.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    List<Comment> getCommentsByUserId(Long userId);
    List<Comment> getCommentsByTaskId(Long taskId);
    Optional<Comment> getCommentById(Long commentId);
    Comment createComment(Comment comment);
    Comment updateComment(Long commentId, String newText);
    void deleteComment(Long commentId);
}