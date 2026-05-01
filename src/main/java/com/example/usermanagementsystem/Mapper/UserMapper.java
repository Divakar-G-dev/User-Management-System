package com.example.usermanagementsystem.Mapper;

import com.example.usermanagementsystem.DTO.RequestDTO.UserRequest;
import com.example.usermanagementsystem.DTO.ResponseDTO.UserResponse;
import com.example.usermanagementsystem.Entity.UserModel;

public class UserMapper {
    public static UserModel toEntity(UserRequest request){
        return UserModel.builder()
                .name(request.name())
                .age(request.age())
                .email(request.email())
                .password(request.password())
                .role(request.role())
                .build();
    }

    public static UserResponse toResponse(UserModel userModel){
        return new UserResponse(
                userModel.getId(),
                userModel.getName(),
                userModel.getEmail(),
                userModel.getAge(),
                userModel.getRole()
        );
    }
}


