package com.buccodev.tech_shop.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.MethodNotAllowedException;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ControllerErrorAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> handlerResourceNotFoundException(ResourceNotFoundException e, HttpServletRequest request) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        var status = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(new StandardError(timestamp, status.value(), request.getRequestURI(), List.of(Map.of("message", e.getMessage()))));
    }

    @ExceptionHandler(ResourceDuplicateException.class)
    public ResponseEntity<StandardError> handlerResourceDuplicateException(ResourceDuplicateException e, HttpServletRequest request) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        var status = HttpStatus.CONFLICT;
        return ResponseEntity.status(status).body(new StandardError(timestamp, status.value(), request.getRequestURI(), List.of(Map.of("message", e.getMessage()))));
    }

    @ExceptionHandler(CredentialInvalidException.class)
    public ResponseEntity<StandardError> handlerCredentialInvalidException(CredentialInvalidException e, HttpServletRequest request) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        var status = HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status).body(new StandardError(timestamp, status.value(), request.getRequestURI(), List.of(Map.of("message", e.getMessage()))));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request) {

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

        List<Map<String, String>> errors = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> Map.of(
                        "field", fieldError.getField(),
                        "message", fieldError.getDefaultMessage()
                ))
                .toList();

        return ResponseEntity.status(status)
                .body(new StandardError(timestamp, status.value(), request.getRequestURI(), errors));
    }

    @ExceptionHandler(OrderItemProcessableException.class)
    public ResponseEntity<StandardError> handlerOrderItemProcessableException(OrderItemProcessableException e, HttpServletRequest request) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        var status = HttpStatus.UNPROCESSABLE_ENTITY;
        return ResponseEntity.status(status).body(new StandardError(timestamp, status.value(), request.getRequestURI(), List.of(Map.of("message", e.getMessage()))));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<StandardError> handlerExceptionGlobal(RuntimeException e, HttpServletRequest request) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(status).body(new StandardError(timestamp, status.value(), request.getRequestURI(), List.of(Map.of("message", e.getMessage()))));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<StandardError> handlerAccessDeniedException(AccessDeniedException e, HttpServletRequest request) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        var status = HttpStatus.FORBIDDEN;
        return ResponseEntity.status(status).body(new StandardError(timestamp, status.value(), request.getRequestURI(), List.of(Map.of("message", e.getMessage()))));
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<StandardError> handlerAuthenticationCredentialsNotFoundException(AuthenticationCredentialsNotFoundException e, HttpServletRequest request) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        var status = HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status).body(new StandardError(timestamp, status.value(), request.getRequestURI(), List.of(Map.of("message", e.getMessage()))));
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    public ResponseEntity<StandardError> handlerMethodNotAllowedException(MethodNotAllowedException e, HttpServletRequest request) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        var status = HttpStatus.METHOD_NOT_ALLOWED;
        return ResponseEntity.status(status).body(new StandardError(timestamp, status.value(), request.getRequestURI(), List.of(Map.of("message", e.getMessage()))));
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<StandardError> handlerAuthorizationDeniedException(AuthorizationDeniedException e, HttpServletRequest request) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        var status = HttpStatus.FORBIDDEN;
        return ResponseEntity.status(status).body(new StandardError(timestamp, status.value(), request.getRequestURI(), List.of(Map.of("message", e.getMessage()))));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<StandardError> handlerHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        var status = HttpStatus.METHOD_NOT_ALLOWED;
        return ResponseEntity.status(status).body(new StandardError(timestamp, status.value(), request.getRequestURI(), List.of(Map.of("message", e.getMessage()))));
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<StandardError> handlerTokenExpiredException(TokenExpiredException e, HttpServletRequest request) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        var status = HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status).body(new StandardError(timestamp, status.value(), request.getRequestURI(), List.of(Map.of("message", e.getMessage()))));
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<StandardError> handlerTokenInvalidException(InvalidTokenException e, HttpServletRequest request) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        var status = HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status).body(new StandardError(timestamp, status.value(), request.getRequestURI(), List.of(Map.of("message", e.getMessage()))));
    }

}
