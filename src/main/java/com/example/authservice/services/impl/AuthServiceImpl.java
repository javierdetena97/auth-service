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
        // Check email
        User userRegistered = userRepository.findByEmail(userRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        // Check password
        if (!passwordEncoder.matches(userRequest.getPassword(), userRegistered.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        TokenResponse token = jwtService.generateToken(userRegistered.getId());
        return token;
    }

    private User mapToEntity(UserRequest userRequest) {
        return User.builder()
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .role("USER")
                .build();
    }

}
