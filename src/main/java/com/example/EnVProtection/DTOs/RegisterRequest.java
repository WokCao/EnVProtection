package com.example.EnVProtection.DTOs;

import com.example.EnVProtection.Enums.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String email;
    private String password;
    private Role role;
}
