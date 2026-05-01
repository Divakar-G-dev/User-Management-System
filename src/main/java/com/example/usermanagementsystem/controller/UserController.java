package com.example.usermanagementsystem.controller;

import com.example.usermanagementsystem.DTO.RequestDTO.UserRequest;
import com.example.usermanagementsystem.DTO.ResponseDTO.APIResponse;
import com.example.usermanagementsystem.DTO.ResponseDTO.UserResponse;
import com.example.usermanagementsystem.Entity.UserModel;
import com.example.usermanagementsystem.service.IUserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/user")
@EnableMethodSecurity
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequest userRequest){
        log.info("Successfully Entered into the Create User API");
        userService.createUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new APIResponse<>(null,"User Created Successfully",true));
    }

    @PostMapping("/create-bulk")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<APIResponse<List<UserResponse>>> createBulkUser(@RequestBody List<UserRequest> userRequest){
        log.info("Successfully Entered into the Create bulk User API");
        return ResponseEntity.status(HttpStatus.CREATED).body(new APIResponse<>(userService.createBulkUser(userRequest),"Bulk Users Created Successfully",true));
    }

    @GetMapping("/get-user/{id}")
    public ResponseEntity<APIResponse<UserResponse>> getUserById(@PathVariable Long id){
        log.info("Successfully Entered into the Get User By Id API");
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse<>(userService.getUserById(id),"User fetched by Id Successfully",true));
    }

    @GetMapping("/get-users")
    public ResponseEntity<APIResponse<List<UserResponse>>> getAllUser(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "age") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending
    ){
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        log.info("Successfully Entered into the Get All User API");
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse<>(userService.getAllUser(pageable),"Fetched All Users Successfully",true));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<APIResponse<UserResponse>> updateUser(@PathVariable Long id,@RequestBody UserRequest userRequest){
        log.info("Successfully Entered into the Update User API");
        UserResponse user = userService.updateUser(id,userRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse<>(user,"User Updated Successfully",true));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<APIResponse<?>> deleteUser(@PathVariable Long id){
        log.info("Successfully Entered into the Delete User API");
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new APIResponse<>(null,"User Deleted Successfully",true));
    }

    @GetMapping("/search")
    public ResponseEntity<APIResponse<List<UserResponse>>> searchArticles(@RequestParam String search) {
        List<UserResponse> foundArticles = userService.searchUserByName(search);
        if (!foundArticles.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FOUND).body(new APIResponse<>(foundArticles,"Found the User By Name",true));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse<>(null,"Found the User By Name",true));
        }
    }

}
