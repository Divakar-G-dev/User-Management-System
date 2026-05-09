package com.example.usermanagementsystem.DTO.OrderDTO.OrderRequest;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record OrderRequestDTO(
        @NotBlank(message = "Product Name Cannot be Empty")
        @Pattern(
                regexp = "^[A-Za-z ]+$",
                message = "Name must contain only alphabets"
        )
        String productName,
        @Min(message = "Amount must be greater than 0", value = 1)
        Integer amount
) {
}
