package com.example.EnVProtection.Models;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@DiscriminatorValue("VOLUNTEER")
@Getter @Setter @NoArgsConstructor @ToString(callSuper = true)
public class Volunteer extends User {
    @Column
    private Integer age;
}
