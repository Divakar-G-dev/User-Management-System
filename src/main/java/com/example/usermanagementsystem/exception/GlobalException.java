package com.example.usermanagementsystem.exception;

import com.example.usermanagementsystem.DTO.APIResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
@Slf4j
public class GlobalException {
    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<APIResponse<?>> handleUserNotFound(UserNotFound ex) {
        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse<>(null, ex.getMessage(), false));
    }

    @ExceptionHandler(OrderNotFound.class)
    public ResponseEntity<APIResponse<?>> handleOrderNotFound(OrderNotFound ex) {
        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse<>(null, ex.getMessage(), false));
    }

    @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    public ResponseEntity<APIResponse<?>> handleConstraintViolation(jakarta.validation.ConstraintViolationException ex) {
        String errorMessage = ex.getConstraintViolations().stream()
                .map(error -> error.getMessage())
                .findFirst()
                .orElse("Validation failed");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new APIResponse<>(null, errorMessage,false));
    }

    @ExceptionHandler(io.jsonwebtoken.ExpiredJwtException.class)
    public ResponseEntity<APIResponse<?>> handleTokenExpired(ExpiredJwtException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new APIResponse<>(null,"Your Token Has Expired",false));
    }

    @ExceptionHandler(io.jsonwebtoken.security.SignatureException.class)
    public ResponseEntity<APIResponse<?>> handleInvalidToken(SignatureException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new APIResponse<>(null,"Invalid Token",false));
    }

    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public ResponseEntity<APIResponse<?>> handleDataIntegrity(org.springframework.dao.DataIntegrityViolationException ex) {
        String message = "";
        if (ex.getMostSpecificCause().getMessage().contains("email")) {
            message = "Email is already registered. Please use a different one";
        }
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new APIResponse<>(null, message, false));
    }

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<APIResponse<?>> handleAccessDenied(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new APIResponse<>(null, "Access Denied", false));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<APIResponse<?>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.badRequest().body(new APIResponse<>(null, "Invalid request parameter", false));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<APIResponse<?>> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(new APIResponse<>(null, "HTTP method not allowed for this request", false));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<APIResponse<?>> handleInvalidEnum(HttpMessageNotReadableException ex) {
        if (ex.getMessage().contains("Role")) {
            return ResponseEntity.badRequest().body(new APIResponse<>(null, "Invalid role value provided", false));
        }
        return ResponseEntity.badRequest().body(new APIResponse<>(null, "Request body is missing or malformed", false));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<APIResponse<?>> handleNoHandlerFound(NoHandlerFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse<>(null, "API endpoint not found", false));
    }

}
