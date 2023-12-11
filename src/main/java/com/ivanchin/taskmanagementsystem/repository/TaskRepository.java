package com.ivanchin.taskmanagementsystem.repository;

import com.ivanchin.taskmanagementsystem.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAuthorId(Long authorId);
    List<Task> findByAssigneeId(Long assigneeId);
}