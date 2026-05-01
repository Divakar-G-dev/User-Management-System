package com.example.usermanagementsystem.DTO.RequestDTO;

import com.example.usermanagementsystem.Enum.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record UserRequest(
        @NotBlank(message = "Name cannot be Empty")
        String name,
        @Positive(message = "Age must be greater than 0")
        Integer age,
        @NotBlank(message = "Name cannot be Empty")
        String email,
        @NotBlank(message = "Password cannot be Empty")
        String password,
        @Enumerated(EnumType.STRING)
        Role role
        )
{ }
