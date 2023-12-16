package com.ivanchin.taskmanagementsystem.controller;

import com.ivanchin.taskmanagementsystem.dto.UserDTO;
import com.ivanchin.taskmanagementsystem.model.User;
import com.ivanchin.taskmanagementsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/by-name/{userName}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<User> getUserByName(@PathVariable String userName) {
        User user = userService.getUserByName(userName);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/registration")
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDto) {
        User createdUser = userService.createUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PatchMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody UserDTO userDto) {
        User updatedUser = userService.updateUser(userId, userDto);
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
