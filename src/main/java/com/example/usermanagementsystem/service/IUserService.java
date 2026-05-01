package com.example.usermanagementsystem.service;

import com.example.usermanagementsystem.DTO.RequestDTO.UserRequest;
import com.example.usermanagementsystem.DTO.ResponseDTO.UserResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IUserService {
    void createUser(UserRequest userRequest);

    UserResponse getUserById(Long id);

    List<UserResponse> getAllUser(Pageable pageable);

    UserResponse updateUser(Long id,UserRequest userRequest);

    void deleteUser(Long id);

    List<UserResponse> createBulkUser(List<UserRequest> userRequest);

    List<UserResponse> searchUserByName(String search);
}
