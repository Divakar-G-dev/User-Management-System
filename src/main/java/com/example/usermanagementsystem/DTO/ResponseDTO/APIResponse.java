package com.example.usermanagementsystem.DTO.ResponseDTO;

public record APIResponse<T>(
        T data,
        String message,
        boolean isSuccess
) {
}
