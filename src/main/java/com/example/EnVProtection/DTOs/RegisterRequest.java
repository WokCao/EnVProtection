package com.example.EnVProtection.DTOs;

import com.example.EnVProtection.Enums.Role;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String email;
    private String password;
    private Role role;
    private String fullName;
    private String address;
    private String phoneNumber;

    // Fields for volunteer
    private Integer age;

    // Fields for organization
    private String description;
    private String logo;
    private String website;
    private Date foundedDate;
}

