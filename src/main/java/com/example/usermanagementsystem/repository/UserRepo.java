package com.example.usermanagementsystem.repository;

import com.example.usermanagementsystem.Entity.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserModel,Long> {
    Optional<UserModel> findByEmail(String email);
    @Query("SELECT s FROM UserModel s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<UserModel> findUserBySearch(@Param("search") String search);

}