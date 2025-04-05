package com.example.EnVProtection.DTOs;

import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileRequest {
    private String fullName;
    private String phoneNumber;
    private String address;
    private int age;
    private String description;
    private String website;
}
