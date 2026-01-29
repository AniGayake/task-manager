package com.task.manager.exception;

public class AuthenticationFailedException extends RuntimeException {
    public AuthenticationFailedException() {
        super("Authentication failed: Invalid username or password");
    }

    public AuthenticationFailedException(String msg) {
        super(msg);
    }
}
