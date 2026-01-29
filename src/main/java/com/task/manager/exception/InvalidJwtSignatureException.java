package com.task.manager.exception;

public class InvalidJwtSignatureException extends RuntimeException {

    public InvalidJwtSignatureException() {
        super("Invalid JWT signature. Token cannot be trusted.");
    }

    public InvalidJwtSignatureException(String message) {
        super(message);
    }

    public InvalidJwtSignatureException(String message, Throwable cause) {
        super(message, cause);
    }
}
