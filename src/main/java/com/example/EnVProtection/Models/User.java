package com.example.EnVProtection.Models;

import com.example.EnVProtection.Enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String fullName;
    private String address;
    private String phoneNumber;
    private Integer age;

    private String organizationName;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String logo;
    private String website;
    private Date foundedDate;
}