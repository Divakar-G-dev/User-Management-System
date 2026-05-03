package com.example.usermanagementsystem.controller;

import com.example.usermanagementsystem.DTO.RequestDTO.AuthRequest;
import com.example.usermanagementsystem.DTO.ResponseDTO.APIResponse;
import com.example.usermanagementsystem.DTO.ResponseDTO.TokenResponse;
import com.example.usermanagementsystem.service.UserInfo;
import com.example.usermanagementsystem.service.JwtService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthorizeController {

    private final UserInfo service;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<APIResponse<TokenResponse>> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        log.info("Login request received for email={}", authRequest.email());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.password())
            );
            log.info("inside try");
            if (authentication.isAuthenticated()) {
                String role = authentication.getAuthorities().stream()
                        .map(auth -> auth.getAuthority())
                        .findFirst()
                        .orElse("ROLE_USER");
                TokenResponse token = jwtService.generateToken(authRequest.email(),role);
                log.info("Authentication successful for email={}", authRequest.email());
                return ResponseEntity.status(HttpStatus.OK).body(new APIResponse<>(token, "Login Successfully", true));
            }
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new APIResponse<>(null, "Invalid email or password", false));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}