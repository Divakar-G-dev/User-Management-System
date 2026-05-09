package com.example.usermanagementsystem.Mapper.OrderMapper;

import com.example.usermanagementsystem.DTO.OrderDTO.OrderRequest.OrderRequestDTO;
import com.example.usermanagementsystem.DTO.OrderDTO.OrderResponse.OrderResponseDTO;
import com.example.usermanagementsystem.DTO.UserDTO.UserResponse.UserResponse;
import com.example.usermanagementsystem.Entity.OrderEntity.OrderModel;

public class OrderMapper {
    public static OrderModel toOrderEntity(OrderRequestDTO request){
        return OrderModel.builder()
                .productName(request.productName())
                .amount(request.amount())
                .build();
    }
    public static OrderResponseDTO toOrderResponse(OrderModel orderModel){
        UserResponse userResponse = null;
        if (orderModel.getUserModel() != null) {
            userResponse = new UserResponse(
                    orderModel.getUserModel().getId(),
                    orderModel.getUserModel().getName(),
                    orderModel.getUserModel().getEmail(),
                    orderModel.getUserModel().getAge(),
                    orderModel.getUserModel().getRole()
            );
        }
        return new OrderResponseDTO(
                orderModel.getId(),
                orderModel.getProductName(),
                orderModel.getAmount(),
                orderModel.getStatus(),
                userResponse
        );
    }
}
