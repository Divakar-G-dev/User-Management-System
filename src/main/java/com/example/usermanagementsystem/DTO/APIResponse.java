package com.example.usermanagementsystem.DTO;

public record APIResponse<T>(
        T data,
        String message,
        boolean isSuccess
) {
}
