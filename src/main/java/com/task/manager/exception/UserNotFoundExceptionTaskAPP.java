package com.task.manager.exception;

public class UserNotFoundExceptionTaskAPP extends RuntimeException {
    public UserNotFoundExceptionTaskAPP(Long id) {
        super("User not found with id: " + id);
    }

    public UserNotFoundExceptionTaskAPP(String msg) {
        super(msg);
    }
}
