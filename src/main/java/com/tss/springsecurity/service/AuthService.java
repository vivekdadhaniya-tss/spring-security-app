package com.tss.springsecurity.service;

import com.tss.springsecurity.dto.request.LoginRequest;
import com.tss.springsecurity.dto.request.RegistrationRequest;
import com.tss.springsecurity.dto.response.JwtAuthResponse;
import com.tss.springsecurity.dto.response.UserResponse;

public interface AuthService {

    UserResponse register(RegistrationRequest request);

    JwtAuthResponse login(LoginRequest request);
}
