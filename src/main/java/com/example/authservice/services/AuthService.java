package com.example.authservice.services;

import com.example.authservice.commons.dtos.TokenResponse;
import com.example.authservice.commons.dtos.UserRequest;

public interface AuthService {
    TokenResponse registerUser(UserRequest userRequest);
    TokenResponse loginUser(UserRequest userRequest);
}
