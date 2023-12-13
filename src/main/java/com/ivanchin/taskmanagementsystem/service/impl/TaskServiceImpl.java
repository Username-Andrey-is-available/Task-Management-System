package com.ivanchin.taskmanagementsystem.service.impl;

import com.ivanchin.taskmanagementsystem.dto.TaskUpdateDTO;
import com.ivanchin.taskmanagementsystem.model.Task;
import com.ivanchin.taskmanagementsystem.model.TaskPriority;
import com.ivanchin.taskmanagementsystem.model.TaskStatus;
import com.ivanchin.taskmanagementsystem.repository.TaskRepository;
import com.ivanchin.taskmanagementsystem.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public List<Task> getTasksByAuthorId(Long authorId) {
        return taskRepository.findByAuthorId(authorId);
    }

    @Override
    public List<Task> getTasksByAssigneeId(Long assigneeId) {
        return taskRepository.findByAssigneeId(assigneeId);
    }

    @Override
    public Optional<Task> getTaskById(Long taskId) {
        return taskRepository.findById(taskId);
    }

    @Override
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Long taskId, TaskUpdateDTO taskUpdateDto) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isPresent()) {
            Task existingTask = optionalTask.get();

            if (taskUpdateDto.getStatus() != null) {
                existingTask.setStatus(TaskStatus.valueOf(taskUpdateDto.getStatus()));
            }

            if (taskUpdateDto.getPriority() != null) {
                existingTask.setPriority(TaskPriority.valueOf(taskUpdateDto.getPriority()));
            }

            if (taskUpdateDto.getAuthor() != null) {
                existingTask.setAuthor(taskUpdateDto.getAuthor());
            }

            if (taskUpdateDto.getAssignee() != null) {
                existingTask.setAssignee(taskUpdateDto.getAssignee());
            }

            return taskRepository.save(existingTask);
        } else {
            // Обработка случая, когда задачи с заданным ID не существует
            return null;
        }
    }


    @Override
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    @Override
    public void changeTaskStatus(Long taskId, String status) {
        // Ваш код изменения статуса задачи
    }

    @Override
    public void assignTask(Long taskId, Long assigneeId) {
        // Ваш код назначения исполнителя задачи
    }
}