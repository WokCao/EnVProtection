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

    // Fields for volunteer
    private String fullName;
    private String address;
    private String phoneNumber;
    private Integer age;

    // Fields for organization
    private String organizationName;
    private String description;
    private String logo;
    private String website;
    private Date foundedDate;
}

