package com.example.usermanagementsystem.DTO.UserDTO.UserRequest;

import com.example.usermanagementsystem.Enum.UserEnum.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;

public record UserPatch(
        @NotBlank(message = "Name cannot be Empty")
        @Pattern(
                regexp = "^[A-Za-z ]+$",
                message = "Name must contain only alphabets"
        )
        String name,
        @Min(value = 18, message = "Age must be greater than 18")
        @Max(value = 99, message = "Age must be less than 99")
        Integer age,
        @Email(message = "Enter a valid Email")
        @Column(unique = true)
        String email,
        @NotBlank(message = "Password cannot be Empty")
        @Pattern(
                regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,14}$",
                message = "Password must be 8-14 characters long and include at least one uppercase letter, one lowercase letter, and one special character."
        )
        String password,
        @NotNull(message = "Role must be a ADMIN or USER cannot be empty")
        @Enumerated(EnumType.STRING)
        Role role
) {
}
