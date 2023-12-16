package com.ivanchin.taskmanagementsystem.controller;

import com.ivanchin.taskmanagementsystem.dto.CommentDTO;
import com.ivanchin.taskmanagementsystem.model.Comment;
import com.ivanchin.taskmanagementsystem.service.CommentService;
import com.ivanchin.taskmanagementsystem.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final TaskService taskService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<Comment>> getAllComments() {
        return new ResponseEntity<>(commentService.getAllComments(), HttpStatus.OK);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long commentId) {
        return commentService.getCommentById(commentId).map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/tasks/{taskId}")
    public ResponseEntity<Comment> createComment(@PathVariable Long taskId, @RequestBody CommentDTO commentDTO,
                                                 @AuthenticationPrincipal UserDetails userDetails) {
        return new ResponseEntity<>(commentService.createComment(taskId, commentDTO, userDetails),
                HttpStatus.CREATED);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long commentId, @RequestBody String newText,
                                                 @AuthenticationPrincipal UserDetails userDetails) {
        Comment updatedComment = commentService.updateComment(commentId, newText, userDetails);
        if (updatedComment != null) {
            return new ResponseEntity<>(updatedComment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
