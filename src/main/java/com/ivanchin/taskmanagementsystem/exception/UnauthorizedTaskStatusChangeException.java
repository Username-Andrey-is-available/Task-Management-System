package com.ivanchin.taskmanagementsystem.exception;

public class UnauthorizedTaskStatusChangeException extends RuntimeException {
    public UnauthorizedTaskStatusChangeException(String message) {
        super(message);
    }
}

