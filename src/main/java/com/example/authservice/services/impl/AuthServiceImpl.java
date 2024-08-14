package com.example.authservice.services.impl;

import com.example.authservice.commons.dtos.TokenResponse;
import com.example.authservice.commons.dtos.UserRequest;
import com.example.authservice.repositories.UserRepository;
import com.example.authservice.services.AuthService;
import com.example.authservice.services.JwtService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthServiceImpl(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    public TokenResponse createUser(UserRequest userRequest) {
        return null;
    }

}
