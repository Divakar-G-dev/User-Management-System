package com.example.usermanagementsystem.repository.OrderRepo;

import com.example.usermanagementsystem.Entity.OrderEntity.OrderModel;
import com.example.usermanagementsystem.Entity.UserEntity.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<OrderModel,Long> {
    List<OrderModel> findByUserModelId(Long id);

    boolean existsByUserModelId(Long userId);

    @Query("SELECT s FROM OrderModel s WHERE LOWER(s.productName) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<OrderModel> findByProductName(@Param("search") String search);

}
