package com.ivanchin.taskmanagementsystem.service.impl;

import com.ivanchin.taskmanagementsystem.dto.UserDTO;
import com.ivanchin.taskmanagementsystem.model.User;
import com.ivanchin.taskmanagementsystem.repository.UserRepository;
import com.ivanchin.taskmanagementsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.orElse(null);
    }

    @Override
    public User createUser(UserDTO userDto) {
        User newUser = new User();
        newUser.setEmail(userDto.getEmail());
        newUser.setPassword(userDto.getPassword());
        newUser.setName(userDto.getName());
        return userRepository.save(newUser);
    }

    @Override
    public User updateUser(Long userId, UserDTO userDto) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setName(userDto.getName());
            existingUser.setEmail(userDto.getEmail());
            existingUser.setPassword(userDto.getPassword());
            return userRepository.save(existingUser);
        } else {
            return null;
        }
    }


    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
