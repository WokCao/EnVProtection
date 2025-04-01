package com.example.EnVProtection.Models;

import com.example.EnVProtection.Enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(insertable = false, updatable = false)
    private Role role;

    protected String fullName;
    private String address;
    private String phoneNumber;

    @Column(nullable = false, updatable = false)
    private Date createdAt = new Date();
}
