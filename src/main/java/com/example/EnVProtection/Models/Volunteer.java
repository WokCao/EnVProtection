package com.example.EnVProtection.Models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("VOLUNTEER")
@Getter @Setter @NoArgsConstructor
public class Volunteer extends User {
    private Integer age;
}
