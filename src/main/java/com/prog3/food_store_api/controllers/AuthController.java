package com.prog3.food_store_api.controllers;

import com.prog3.food_store_api.DTOs.AuthResponse;
import com.prog3.food_store_api.DTOs.UserCreate;
import com.prog3.food_store_api.DTOs.UserLogin;
import com.prog3.food_store_api.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody UserCreate dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody UserLogin dto) {
        return ResponseEntity.ok(authService.login(dto));
    }
}
