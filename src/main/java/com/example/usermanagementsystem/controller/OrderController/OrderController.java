package com.example.usermanagementsystem.controller.OrderController;

import com.example.usermanagementsystem.DTO.OrderDTO.OrderRequest.OrderPatchDTO;
import com.example.usermanagementsystem.DTO.OrderDTO.OrderRequest.OrderRequestDTO;
import com.example.usermanagementsystem.DTO.OrderDTO.OrderResponse.OrderResponseDTO;
import com.example.usermanagementsystem.DTO.APIResponse;
import com.example.usermanagementsystem.service.OrderService.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {
    private final IOrderService orderService;

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ResponseEntity<APIResponse<OrderResponseDTO>> createOrder(@RequestBody OrderRequestDTO orderRequestDTO){
        log.info("Successfully Entered into the Create Order API");
        return ResponseEntity.status(HttpStatus.CREATED).body(new APIResponse<>(orderService.createOrder(orderRequestDTO),"Order Created Successfully",true));
    }

    @GetMapping("/get-orders-id")
    public ResponseEntity<APIResponse<List<OrderResponseDTO>>> getOrdersById(){
        log.info("Successfully Entered into the Get Orders By Id API");
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse<>(orderService.getOrdersByUser(),"Fetched Orders By User Id",true));
    }

    @GetMapping("/get-all-orders")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<APIResponse<List<OrderResponseDTO>>> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam(defaultValue = "amount") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending
    ){
        log.info("Successfully Entered into the Get All Orders API");
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page <= 0 ? 0 : size - 1, size <= 0 ? 2 : size, sort);
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse<>(orderService.getAllOrders(pageable),"Fetched All Orders Successfully",true));
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<APIResponse<List<OrderResponseDTO>>> searchByProductName(@RequestParam String search) {
        log.info("Successfully Entered into the Search Product by Name API");
        List<OrderResponseDTO> foundProduct = orderService.searchByProductName(search);
        if (!foundProduct.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FOUND).body(new APIResponse<>(foundProduct,"Found the Product By Name",true));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse<>(null,"Order Not Exist",false));
        }
    }

    @PatchMapping("/update-order-particular/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<APIResponse<OrderResponseDTO>> updateOrderParticular(@PathVariable Long id, @RequestBody OrderPatchDTO orderPatchDTO){
        log.info("Successfully Entered into the Patch API of Order");
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse<>(orderService.updateOrderParticular(id,orderPatchDTO),"Order Updated Successfully",true));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<APIResponse<?>> deleteOrder(@PathVariable Long id){
        log.info("Successfully Entered into the Delete API of Order");
        orderService.deleteOrder(id);
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse<>(null,"Order Deleted Successfully",true));
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<APIResponse<OrderResponseDTO>> cancelOrder(@PathVariable Long id){
        log.info("Successfully Entered into the Cancel Order API");
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse<>(orderService.cancelOrder(id),"Order Canceled Successfully",true));
    }
}
