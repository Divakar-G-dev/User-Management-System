package com.example.usermanagementsystem.DTO.OrderDTO.OrderResponse;

import com.example.usermanagementsystem.DTO.UserDTO.UserResponse.UserResponse;
import com.example.usermanagementsystem.Enum.OrderEnum.Status;

import java.io.Serializable;

public record OrderResponseDTO(Long id, String productName, Integer amount, Status status, UserResponse userResponse) implements Serializable {
}
