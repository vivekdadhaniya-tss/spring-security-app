package com.tss.springsecurity.controller;

import com.tss.springsecurity.dto.request.LoginRequest;
import com.tss.springsecurity.dto.request.RegistrationRequest;
import com.tss.springsecurity.dto.response.JwtAuthResponse;
import com.tss.springsecurity.dto.response.UserResponse;
import com.tss.springsecurity.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody RegistrationRequest request) {
        UserResponse response = authService.register(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> loginUser(@RequestBody LoginRequest request) {
        JwtAuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}