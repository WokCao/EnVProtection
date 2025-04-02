package com.example.EnVProtection.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@DiscriminatorValue("ORGANIZATION")
@Getter @Setter @NoArgsConstructor @ToString(callSuper = true)
public class Organization extends User {
    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private String logo;

    @Column
    private String website;

    @Column
    private Date foundedDate;
}