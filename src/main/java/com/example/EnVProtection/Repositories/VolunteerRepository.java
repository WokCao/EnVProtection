package com.example.EnVProtection.Repositories;

import com.example.EnVProtection.Models.User;
import com.example.EnVProtection.Models.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VolunteerRepository extends JpaRepository<User, Long> {
    Optional<Volunteer> findByEmail(String email);
}