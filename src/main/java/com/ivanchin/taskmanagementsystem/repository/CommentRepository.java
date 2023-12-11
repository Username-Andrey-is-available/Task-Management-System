package com.ivanchin.taskmanagementsystem.repository;

import com.ivanchin.taskmanagementsystem.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByUserId(Long userId);
    List<Comment> findByTaskId(Long taskId);
}
