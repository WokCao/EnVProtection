package com.example.EnVProtection.Models;

import com.example.EnVProtection.Enums.ProjectStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "projects")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String briefDescription;

    @Column(nullable = false)
    private String fullDescription;

    @Column(nullable = false)
    private String image;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime time;

    @ElementCollection
    private List<String> requirements;

    @Column
    private String impact;

    @Column(nullable = false)
    private Long volunteersNeeded;

    @ManyToOne
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization hostOrganization;

    @ManyToMany
    @JoinTable(
            name = "volunteer_projects",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "volunteer_id")
    )
    private Set<User> volunteers = new HashSet<>();
}

