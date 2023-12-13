package com.ivanchin.taskmanagementsystem.service.impl;

import com.ivanchin.taskmanagementsystem.model.Comment;
import com.ivanchin.taskmanagementsystem.repository.CommentRepository;
import com.ivanchin.taskmanagementsystem.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public Optional<Comment> getCommentById(Long commentId) {
        return commentRepository.findById(commentId);
    }

    @Override
    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(Long commentId, String newText) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isPresent()) {
            Comment existingComment = optionalComment.get();
            existingComment.setText(newText);
            return commentRepository.save(existingComment);
        } else {
            return null;
        }
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
