package com.example.usermanagementsystem.service.OrderService;

import com.example.usermanagementsystem.DTO.OrderDTO.OrderRequest.OrderPatchDTO;
import com.example.usermanagementsystem.DTO.OrderDTO.OrderRequest.OrderRequestDTO;
import com.example.usermanagementsystem.DTO.OrderDTO.OrderResponse.OrderResponseDTO;
import com.example.usermanagementsystem.DTO.UserDTO.UserResponse.UserResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface IOrderService {

    OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO);

    @Cacheable(value = "orders", key = "#userId")
    List<OrderResponseDTO> getOrdersByUser();

    @Cacheable(value = "all_orders")
    List<OrderResponseDTO> getAllOrders(Pageable pageable);

    List<OrderResponseDTO> searchByProductName(String search);

    OrderResponseDTO updateOrderParticular(Long id, OrderPatchDTO orderPatchDTO);

    void deleteOrder(Long id);

    OrderResponseDTO cancelOrder(Long id);
}
