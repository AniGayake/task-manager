package com.task.manager.exception;
public class UnauthorizedActionException extends RuntimeException {
    public UnauthorizedActionException(String msg) {
        super(msg);
    }
}
