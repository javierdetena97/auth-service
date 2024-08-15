package com.example.authservice.controllers;

import com.example.authservice.commons.constants.ApiPathConstants;
import com.example.authservice.commons.dtos.TokenResponse;
import com.example.authservice.commons.dtos.UserRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(ApiPathConstants.V1_ROUTE + ApiPathConstants.AUTH_ROUTE)
public interface AuthApi {

    @PostMapping(value = "/register")
    ResponseEntity<TokenResponse> registerUser(@RequestBody @Valid UserRequest userRequest);

    @PostMapping(value = "/login")
    ResponseEntity<TokenResponse> loginUser(@RequestBody @Valid UserRequest userRequest);

    @GetMapping
    ResponseEntity<String> getUser(@RequestAttribute(name = "X-User-Id") String userId);
}
