package com.ivanchin.taskmanagementsystem.service;

import com.ivanchin.taskmanagementsystem.dto.UserUpdateDTO;
import com.ivanchin.taskmanagementsystem.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();

    Optional<User> getUserById(Long userId);

    Optional<User> getUserByEmail(String email);

    User createUser(User user);

    User updateUser(Long userId, UserUpdateDTO userUpdateDto);

    void deleteUser(Long userId);
}