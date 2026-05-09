package com.example.usermanagementsystem.service.UserService;

import com.example.usermanagementsystem.DTO.UserDTO.UserRequest.UserPatch;
import com.example.usermanagementsystem.DTO.UserDTO.UserRequest.UserRequest;
import com.example.usermanagementsystem.DTO.UserDTO.UserResponse.UserResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserService {
    UserResponse createUser(UserRequest userRequest);

    UserResponse getUserById(Long id);

    List<UserResponse> getAllUser(Pageable pageable);

    UserResponse updateUser(Long id,UserRequest userRequest);

    void deleteUser(Long id);

    List<UserResponse> createBulkUser(List<UserRequest> userRequest);

    List<UserResponse> searchUserByName(String search);

    UserResponse updateParticular(Long id, UserPatch userPatch);

}
