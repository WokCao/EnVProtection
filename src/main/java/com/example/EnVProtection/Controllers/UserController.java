package com.example.EnVProtection.Controllers;


import com.example.EnVProtection.DTOs.ChangePasswordRequest;
import com.example.EnVProtection.DTOs.UpdateProfileRequest;
import com.example.EnVProtection.Models.User;
import com.example.EnVProtection.Services.UserService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/me")
    public ResponseEntity<User> updateProfile(@RequestBody UpdateProfileRequest request, Principal principal) {
        String email = principal.getName();
        User updatedUser = userService.updateUserProfile(email, request);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/me/password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request, Principal principal) {
        String email = principal.getName();
        userService.changePassword(email, request);
        return ResponseEntity.noContent().build();
    }
}
