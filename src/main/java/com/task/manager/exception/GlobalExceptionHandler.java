package com.task.manager.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;

@ResponseStatus
@ControllerAdvice
public class GlobalExceptionHandler {

    // USER NOT FOUND
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFound(
            UserNotFoundException ex,
            HttpServletRequest req) {

        ApiError error = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                "USER_NOT_FOUND",
                ex.getMessage(),
                req.getRequestURI()
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // TASK NOT FOUND
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ApiError> handleTaskNotFound(
            TaskNotFoundException ex,
            HttpServletRequest req) {

        ApiError error = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                "TASK_NOT_FOUND",
                ex.getMessage(),
                req.getRequestURI()
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // AUTHENTICATION FAILED
    @ExceptionHandler({AuthenticationFailedException.class, BadCredentialsException.class})
    public ResponseEntity<ApiError> handleAuthFailed(
            Exception ex,
            HttpServletRequest req) {

        ApiError error = new ApiError(
                HttpStatus.UNAUTHORIZED.value(),
                "AUTHENTICATION_FAILED",
                "Invalid username or password",
                req.getRequestURI()
        );

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    // UNAUTHORIZED ACTION
    @ExceptionHandler(UnauthorizedActionException.class)
    public ResponseEntity<ApiError> handleUnauthorizedAction(
            UnauthorizedActionException ex,
            HttpServletRequest req) {

        ApiError error = new ApiError(
                HttpStatus.FORBIDDEN.value(),
                "ACCESS_DENIED",
                ex.getMessage(),
                req.getRequestURI()
        );

        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    // ACCESS DENIED (Spring Security)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDenied(
            AccessDeniedException ex,
            HttpServletRequest req) {

        ApiError error = new ApiError(
                HttpStatus.FORBIDDEN.value(),
                "ACCESS_DENIED",
                "You are not authorized to perform this action",
                req.getRequestURI()
        );

        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    // GENERIC FALLBACK
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(
            Exception ex,
            HttpServletRequest req) {

        ApiError error = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "INTERNAL_SERVER_ERROR",
                ex.getMessage(),
                req.getRequestURI()
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(InvalidJwtSignatureException.class)
    public ResponseEntity<ApiError> handleInvalidJwtSignature(
            InvalidJwtSignatureException ex,
            HttpServletRequest req) {

        ApiError error = new ApiError(
                HttpStatus.UNAUTHORIZED.value(),
                "INVALID_JWT_SIGNATURE",
                ex.getMessage(),
                req.getRequestURI()
        );

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }
}
