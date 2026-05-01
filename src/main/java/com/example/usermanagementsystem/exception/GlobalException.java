package com.example.usermanagementsystem.exception;

import com.example.usermanagementsystem.DTO.ResponseDTO.APIResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalException {
    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<APIResponse<?>> handleUserNotFound(UserNotFound ex) {
        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse<>(null, ex.getMessage(), false));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse<?>> handleValidationErrors(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new APIResponse<>(null, ex.getMessage(),false));
    }

    @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    public ResponseEntity<APIResponse<?>> handleConstraintViolation(jakarta.validation.ConstraintViolationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new APIResponse<>(null, ex.getMessage(),false));
    }

    @ExceptionHandler(io.jsonwebtoken.ExpiredJwtException.class)
    public ResponseEntity<APIResponse<?>> handleTokenExpired(ExpiredJwtException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new APIResponse<>(null,"Your Token Has Expired",false));
    }

    @ExceptionHandler(io.jsonwebtoken.security.SignatureException.class)
    public ResponseEntity<APIResponse<?>> handleInvalidToken(SignatureException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new APIResponse<>(null,"Invalid Token",false));
    }

}
