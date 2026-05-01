package com.example.usermanagementsystem.DTO.ResponseDTO;

import com.example.usermanagementsystem.Enum.Role;

public record UserResponse(Long id, String name, String email, Integer age, Role role) {

}
