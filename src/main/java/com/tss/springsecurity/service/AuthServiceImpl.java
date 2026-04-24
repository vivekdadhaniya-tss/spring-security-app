package com.tss.springsecurity.service;

import com.tss.springsecurity.dto.request.LoginRequest;
import com.tss.springsecurity.dto.request.RegistrationRequest;
import com.tss.springsecurity.dto.response.JwtAuthResponse;
import com.tss.springsecurity.dto.response.UserResponse;
import com.tss.springsecurity.entity.Role;
import com.tss.springsecurity.entity.User;
import com.tss.springsecurity.exception.UserApiException;
import com.tss.springsecurity.repo.RoleRepository;
import com.tss.springsecurity.repo.UserRepository;
import com.tss.springsecurity.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public UserResponse register(RegistrationRequest request) {

        if(userRepository.existsByUsername(request.getUsername()))
            throw new UserApiException("Username is already taken");

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Role userRole = roleRepository.findByRoleName(request.getRoleName())
                .orElseThrow(() -> new UserApiException("User Role not exists"));

        user.setRole(userRole);

        User savedUser = userRepository.save(user);

        return UserResponse.builder()
                .userId(savedUser.getUserId())
                .username(savedUser.getUsername())
                .role(savedUser.getRole().getRoleName())
                .build();
    }

    @Override
    public JwtAuthResponse login(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtTokenProvider.generateToken(authentication);

            return JwtAuthResponse.builder()
                    .accessToken(token)
                    .build();

        } catch (BadCredentialsException e) {
            throw new UserApiException("Username or password is incorrect");
        }
    }
}
