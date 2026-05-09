package com.example.usermanagementsystem.DTO.UserDTO.UserResponse;

import com.example.usermanagementsystem.Enum.UserEnum.Role;

import java.io.Serializable;

public record UserResponse(Long id, String name, String email, Integer age, Role role) implements Serializable {

}
