package com.example.EnVProtection.Services;

import com.example.EnVProtection.DTOs.CreateOrganization;
import com.example.EnVProtection.Models.Organization;
import com.example.EnVProtection.Models.User;
import com.example.EnVProtection.Repositories.OrganizationRepository;
import com.example.EnVProtection.Repositories.VolunteerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OrganizationService {
    private final OrganizationRepository organizationRepository;

    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;}

    public List<Organization> getAllOrganizations() {
        return organizationRepository.findAll();
    }
}

