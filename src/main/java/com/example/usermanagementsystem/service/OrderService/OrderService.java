package com.example.usermanagementsystem.service.OrderService;

import com.example.usermanagementsystem.DTO.OrderDTO.OrderRequest.OrderPatchDTO;
import com.example.usermanagementsystem.DTO.OrderDTO.OrderRequest.OrderRequestDTO;
import com.example.usermanagementsystem.DTO.OrderDTO.OrderResponse.OrderResponseDTO;
import com.example.usermanagementsystem.DTO.UserDTO.UserResponse.UserResponse;
import com.example.usermanagementsystem.Entity.OrderEntity.OrderModel;
import com.example.usermanagementsystem.Entity.UserEntity.UserModel;
import com.example.usermanagementsystem.Enum.OrderEnum.Status;
import com.example.usermanagementsystem.Mapper.OrderMapper.OrderMapper;
import com.example.usermanagementsystem.Mapper.UserMapper.UserMapper;
import com.example.usermanagementsystem.exception.OrderNotFound;
import com.example.usermanagementsystem.exception.UserNotFound;
import com.example.usermanagementsystem.repository.OrderRepo.OrderRepo;
import com.example.usermanagementsystem.repository.UserRepo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderService implements IOrderService{
    private final OrderRepo orderRepo;
    private final UserRepo userRepo;
    public OrderService(OrderRepo orderRepo, UserRepo userRepo){
        this.orderRepo=orderRepo;
        this.userRepo = userRepo;
    }

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {
        log.info("Order Created Successfully");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        UserModel user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFound("Cannot create order: User not Exist"));
        OrderModel order = OrderMapper.toOrderEntity(orderRequestDTO);
        order.setUserModel(user);
        orderRepo.save(order);
        return OrderMapper.toOrderResponse(order);
    }


    @Override
    @Cacheable(value = "orders")
    public List<OrderResponseDTO> getOrdersByUser() {
        log.info("Fetching User with ID");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        UserModel user = userRepo.findByEmail(email)
                .orElseThrow(() -> new OrderNotFound("No Orders Found"));
        List<OrderResponseDTO> order = orderRepo.findByUserModelId(user.getId()).stream().map(OrderMapper::toOrderResponse).toList();
        return order;
    }

    @Override
    @Cacheable(value = "all_orders")
    public List<OrderResponseDTO> getAllOrders(Pageable pageable) {
        log.info("Fetching paginated users");
        Page<OrderModel> orderPage = orderRepo.findAll(pageable);
        if (orderPage.isEmpty()) {
            throw new OrderNotFound("No Product found on page: " + pageable.getPageNumber());
        }
        return orderPage.getContent().stream().map(OrderMapper::toOrderResponse).collect(Collectors.toList());
    }

    @Override
    public List<OrderResponseDTO> searchByProductName(String search) {
        List<OrderModel> order = orderRepo.findByProductName(search);
        log.info("Product Found By Search Successfully");
        return order.stream().map(OrderMapper::toOrderResponse).toList();
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "orders", allEntries = true),
            @CacheEvict(value = "all_orders", allEntries = true)
    })
    public OrderResponseDTO updateOrderParticular(Long id, OrderPatchDTO orderPatchDTO) {
        OrderModel order = orderRepo.findById(id).orElseThrow(()-> new OrderNotFound("Order doesn't Exist"));
        if(orderPatchDTO.productName()!=null) order.setProductName(orderPatchDTO.productName());
        if(orderPatchDTO.amount()!=null) order.setAmount(orderPatchDTO.amount());
        return OrderMapper.toOrderResponse(orderRepo.save(order));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "orders", allEntries = true),
            @CacheEvict(value = "all_orders", allEntries = true)
    })
    public void deleteOrder(Long id) {
        OrderModel order = orderRepo.findById(id).orElseThrow(()->new OrderNotFound("Order does not Exists"));
        if(!order.isActive()) {
            throw  new UserNotFound("User Already Deleted");
        }
        order.setActive(false);
        orderRepo.save(order);
        log.info("Order Deleted Successfully");
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "orders", allEntries = true),
            @CacheEvict(value = "all_orders", allEntries = true)
    })
    public OrderResponseDTO cancelOrder(Long id) {
        OrderModel order = orderRepo.findById(id).orElseThrow(()->new OrderNotFound("Order does not exists"));
        if(order.getStatus()== Status.PENDING){
            order.setStatus(Status.CANCEL);
        }
        return OrderMapper.toOrderResponse(orderRepo.save(order));
    }

}
