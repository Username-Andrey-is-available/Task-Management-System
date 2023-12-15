package com.ivanchin.taskmanagementsystem.service;

import com.ivanchin.taskmanagementsystem.dto.UserDTO;
import com.ivanchin.taskmanagementsystem.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUserById(Long userId);

    User createUser(UserDTO userDto);

    User updateUser(Long userId, UserDTO userDTO);

    void deleteUser(Long userId);

    User getUserByName(String userName);
}
