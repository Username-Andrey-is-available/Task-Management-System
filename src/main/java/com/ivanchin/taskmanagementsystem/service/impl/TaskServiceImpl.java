package com.ivanchin.taskmanagementsystem.service.impl;

import com.ivanchin.taskmanagementsystem.dto.TaskDTO;
import com.ivanchin.taskmanagementsystem.model.Task;
import com.ivanchin.taskmanagementsystem.repository.TaskRepository;
import com.ivanchin.taskmanagementsystem.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;


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
        // Логика создания задачи, если нужно
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Long taskId, TaskDTO taskDTO) {
        // Логика обновления задачи, если нужно
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isPresent()) {
            Task existingTask = optionalTask.get();
            // Обновляем поля задачи согласно TaskUpdateDTO
            existingTask.setTitle(taskDTO.getTitle());
            existingTask.setDescription(taskDTO.getDescription());
            // ... другие поля ...

            return taskRepository.save(existingTask);
        } else {
            return null;
        }
    }

    @Override
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }
}
