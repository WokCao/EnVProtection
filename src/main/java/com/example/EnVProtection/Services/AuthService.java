package com.example.EnVProtection.Services;

import com.example.EnVProtection.Enums.Role;
import com.example.EnVProtection.Models.User;
import com.example.EnVProtection.Repositories.UserRepository;
import com.example.EnVProtection.Utils.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public ResponseEntity<?> register(String email, String password, Role role) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("User already exists!");
        }
        User user = new User(null, email, passwordEncoder.encode(password), role);
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password!");
        }
        return ResponseEntity.ok(jwtUtil.generateToken(user.getEmail(), user.getRole()));
    }
}

