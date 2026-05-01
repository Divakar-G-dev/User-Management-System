package com.example.usermanagementsystem.service;

import com.example.usermanagementsystem.DTO.RequestDTO.UserRequest;
import com.example.usermanagementsystem.DTO.ResponseDTO.UserResponse;
import com.example.usermanagementsystem.Entity.UserModel;
import com.example.usermanagementsystem.Mapper.UserMapper;
import com.example.usermanagementsystem.exception.UserNotFound;
import com.example.usermanagementsystem.repository.UserRepo;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class UserService implements IUserService {
    private final UserRepo userRepo;
    private final PasswordEncoder encoder;

    public UserService(UserRepo userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    @Override
    public void createUser(UserRequest userRequest) {
        UserModel user = UserMapper.toEntity(userRequest);
        user.setPassword(encoder.encode(user.getPassword()));
        log.info("User Created Successfully");
        userRepo.save(user);
    }

    @Override
    public UserResponse getUserById(Long id) {
        log.info("Fetching User with ID");
        return userRepo.findById(id)
                .map(UserMapper::toResponse)
                .orElseThrow(() -> new UserNotFound("User not found"));
    }

    @Override
    public List<UserResponse> getAllUser(Pageable pageable) {
        log.info("Fetching paginated users");
        Page<UserModel> userPage = userRepo.findAll(pageable);
        if (userPage.isEmpty()) {
            throw new UserNotFound("No users found on page: " + pageable.getPageNumber());
        }
        return userPage.getContent()
                .stream()
                .map(UserMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        UserModel user = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFound("User not Exist"));
        user.setName(userRequest.name());
        user.setAge(userRequest.age());
        user.setEmail(userRequest.email());
        user.setPassword(userRequest.password());
        UserModel updatedUser = userRepo.save(user);
        return UserMapper.toResponse(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
        log.info("User Deleted Successfully");
    }

    @Override
    public List<UserResponse> createBulkUser(List<UserRequest> userRequest) {
        List<UserModel> list = userRequest.stream().map(user -> {
            UserModel usermodel = UserMapper.toEntity(user);
            usermodel.setPassword(encoder.encode(usermodel.getPassword()));
            return usermodel;
        }).toList();
        return userRepo.saveAll(list).stream().map(UserMapper::toResponse).toList();
    }

    @Override
    public List<UserResponse> searchUserByName(String search) {
        List<UserModel> user = userRepo.findUserBySearch(search);
        return user.stream().map(UserMapper::toResponse).toList();
    }

}
