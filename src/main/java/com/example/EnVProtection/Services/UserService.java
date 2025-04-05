package com.example.EnVProtection.Services;

import com.example.EnVProtection.DTOs.ChangePasswordRequest;
import com.example.EnVProtection.DTOs.UpdateProfileRequest;
import com.example.EnVProtection.Enums.Role;
import com.example.EnVProtection.Models.Organization;
import com.example.EnVProtection.Models.User;
import com.example.EnVProtection.Models.Volunteer;
import com.example.EnVProtection.Repositories.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User updateUserProfile(String email, UpdateProfileRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setFullName(request.getFullName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setAddress(request.getAddress());

        if (user.getRole() == Role.VOLUNTEER) {
            Volunteer volunteer = (Volunteer) user;
            volunteer.setAge(request.getAge());
        } else {
            Organization organization = (Organization) user;
            organization.setDescription(request.getDescription());
            organization.setWebsite(request.getWebsite());
        }
        return userRepository.save(user);
    }

    public void changePassword(String email, ChangePasswordRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Incorrect current password");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}
