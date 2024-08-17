package com.example.authservice.services.impl;

import com.example.authservice.commons.dtos.TokenResponse;
import com.example.authservice.commons.dtos.UserRequest;
import com.example.authservice.commons.entities.User;
import com.example.authservice.repositories.UserRepository;
import com.example.authservice.services.AuthService;
import com.example.authservice.services.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public TokenResponse registerUser(UserRequest userRequest) {
        return Optional.of(userRequest)
                .map(this::mapToEntity)
                .map(userRepository::save)
                .map(userRegistered -> jwtService.generateToken(userRegistered.getId()))
                .orElseThrow(() -> new RuntimeException("Error registering user"));
    }

    @Override
    public TokenResponse loginUser(UserRequest userRequest) {
        return Optional.of(userRequest)
                .map(UserRequest::getEmail)
                .flatMap(userRepository::findByEmail)
                .filter(user -> passwordEncoder.matches(userRequest.getPassword(), user.getPassword()))
                .map(user -> jwtService.generateToken(user.getId()))
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));
    }

    private User mapToEntity(UserRequest userRequest) {
        return User.builder()
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .role("USER")
                .build();
    }

}
