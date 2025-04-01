package com.example.EnVProtection.Services;

import com.example.EnVProtection.DTOs.RegisterRequest;
import com.example.EnVProtection.Enums.Role;
import com.example.EnVProtection.Models.Organization;
import com.example.EnVProtection.Models.User;
import com.example.EnVProtection.Models.Volunteer;
import com.example.EnVProtection.Repositories.OrganizationRepository;
import com.example.EnVProtection.Repositories.UserRepository;
import com.example.EnVProtection.Repositories.VolunteerRepository;
import com.example.EnVProtection.Utils.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final VolunteerRepository volunteerRepository;
    private final OrganizationRepository organizationRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, VolunteerRepository volunteerRepository, OrganizationRepository organizationRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.volunteerRepository = volunteerRepository;
        this.organizationRepository = organizationRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public ResponseEntity<?> register(RegisterRequest request) {
        if (volunteerRepository.findByEmail(request.getEmail()).isPresent() || organizationRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists!");
        }

        if (request.getRole() == Role.VOLUNTEER) {
            Volunteer volunteer = new Volunteer();
            volunteer.setEmail(request.getEmail());
            volunteer.setPassword(passwordEncoder.encode(request.getPassword()));
            volunteer.setRole(request.getRole());
            volunteer.setFullName(request.getFullName());
            volunteer.setAddress(request.getAddress());
            volunteer.setPhoneNumber(request.getPhoneNumber());

            // For volunteer
            volunteer.setAge(request.getAge());

            volunteerRepository.save(volunteer);
            String token = jwtUtil.generateToken(volunteer.getEmail(), volunteer.getRole());

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("user", volunteer);

            return ResponseEntity.ok(response);
        } else if (request.getRole() == Role.ORGANIZATION) {
            Organization organization = new Organization();
            organization.setEmail(request.getEmail());
            organization.setPassword(passwordEncoder.encode(request.getPassword()));
            organization.setRole(request.getRole());
            organization.setFullName(request.getFullName());
            organization.setDescription(request.getDescription());
            organization.setAddress(request.getAddress());
            organization.setPhoneNumber(request.getPhoneNumber());

            // For organization
            organization.setLogo(request.getLogo());
            organization.setWebsite(request.getWebsite());
            organization.setFoundedDate(request.getFoundedDate());

            organizationRepository.save(organization);
            String token = jwtUtil.generateToken(organization.getEmail(), organization.getRole());

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("user", organization);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().body("Invalid user role");
    }

    public ResponseEntity<?> login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password!");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);

        if (user.getRole() == Role.VOLUNTEER) {
            Volunteer volunteer = (Volunteer) user;
            response.put("user", volunteer);
        } else if (user.getRole() == Role.ORGANIZATION) {
            Organization organization = (Organization) user;
            response.put("user", organization);
        }

        return ResponseEntity.ok(response);
    }
}

