package com.example.EnVProtection.Repositories;

import com.example.EnVProtection.Models.Organization;
import com.example.EnVProtection.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    Optional<Organization> findByEmail(String email);
}

