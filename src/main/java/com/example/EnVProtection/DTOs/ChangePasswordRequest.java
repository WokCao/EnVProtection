package com.example.EnVProtection.DTOs;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {
    private String oldPassword;
    private String newPassword;
}
