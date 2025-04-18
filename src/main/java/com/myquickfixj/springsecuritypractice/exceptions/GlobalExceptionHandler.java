package com.myquickfixj.springsecuritypractice.exceptions;
import com.myquickfixj.springsecuritypractice.Dto.CustomMessage;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CustomMessage> handleRuntimeException(RuntimeException ex) {
        CustomMessage error = new CustomMessage();
        error.setStatus(false);
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CustomMessage> handleIllegalArgument(IllegalArgumentException ex) {
        CustomMessage error = new CustomMessage();
        error.setStatus(false);
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CustomMessage> handleAccessDenied(AuthorizationDeniedException ex) {
        CustomMessage error = new CustomMessage();
        error.setStatus(false);
        error.setMessage("Access Denied:");
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomMessage> handleGeneric(Exception ex) {
        CustomMessage error = new CustomMessage();
        error.setStatus(false);
        error.setMessage("An unexpected error occurred: " + ex.getMessage());
        ex.printStackTrace(); // Log or send to monitoring
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
