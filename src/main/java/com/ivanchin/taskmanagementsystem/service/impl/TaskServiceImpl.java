package com.ivanchin.taskmanagementsystem.service.impl;

import com.ivanchin.taskmanagementsystem.dto.TaskDTO;
import com.ivanchin.taskmanagementsystem.model.Task;
import com.ivanchin.taskmanagementsystem.model.TaskPriority;
import com.ivanchin.taskmanagementsystem.model.TaskStatus;
import com.ivanchin.taskmanagementsystem.model.User;
import com.ivanchin.taskmanagementsystem.repository.TaskRepository;
import com.ivanchin.taskmanagementsystem.repository.UserRepository;
import com.ivanchin.taskmanagementsystem.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
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
    public Task updateTask(Long taskId, TaskDTO taskDTO, UserDetails userDetails) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isPresent()) {
            Task existingTask = optionalTask.get();
            Optional<User> user = userRepository.findByName(userDetails.getUsername());
            if (user.isPresent()) {
                if (existingTask.getAssignee().getId() == user.get().getId()) {
                    existingTask.setStatus(TaskStatus.valueOf(taskDTO.getStatus()));
                }
                if (existingTask.getAuthor().getId() == user.get().getId()) {
                    if (taskDTO.getPriority() != null) {
                        existingTask.setPriority(TaskPriority.valueOf(taskDTO.getPriority()));
                    }
                    if(taskDTO.getTitle() != null){
                        existingTask.setTitle(taskDTO.getTitle());

                    }
                    if(taskDTO.getDescription() != null) {
                        existingTask.setDescription(taskDTO.getDescription());
                    }
                }
            }
            return taskRepository.save(existingTask);
        } else {
            return null;
        }
    }


    @Override
    public List<Task> getAllTasksFrom(String email) {
        return userRepository.findByEmail(email)
                .map(user -> taskRepository.findByAuthorId(user.getId()))
                .orElse(Collections.emptyList());
    }

    @Override
    public List<Task> getAllTasksFor(String email) {
        return userRepository.findByEmail(email)
                .map(user -> taskRepository.findByAssigneeId(user.getId()))
                .orElse(Collections.emptyList());
    }


    @Override
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }
}
