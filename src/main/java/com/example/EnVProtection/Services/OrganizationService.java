package com.example.EnVProtection.Services;

import com.example.EnVProtection.DTOs.CreateOrganization;
import com.example.EnVProtection.Models.Organization;
import com.example.EnVProtection.Models.User;
import com.example.EnVProtection.Repositories.OrganizationRepository;
import com.example.EnVProtection.Repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;

    public OrganizationService(OrganizationRepository organizationRepository, UserRepository userRepository) {
        this.organizationRepository = organizationRepository;
        this.userRepository = userRepository;
    }

    public List<Organization> getAllOrganizations() {
        return organizationRepository.findAll();
    }

    public ResponseEntity<?> createOrganization(CreateOrganization organization) {
        Optional<User> optionalUser = userRepository.findById(organization.getOwner().getId());
        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body("Cannot find user");
        }

        Organization organizationObject = new Organization(null, organization.getName(), organization.getDescription(), optionalUser.get());
        organizationRepository.save(organizationObject);
        return ResponseEntity.ok(organizationObject);
    }
}

