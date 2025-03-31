package com.example.EnVProtection.Controllers;

import com.example.EnVProtection.DTOs.LoginRequest;
import com.example.EnVProtection.DTOs.RegisterRequest;
import com.example.EnVProtection.Enums.Role;
import com.example.EnVProtection.Services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        return authService.register(request.getEmail(), request.getPassword(), request.getRole());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return authService.login(request.getEmail(), request.getPassword());
    }
}

