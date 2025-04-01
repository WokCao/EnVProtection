package com.example.EnVProtection.Controllers;

import com.example.EnVProtection.DTOs.CreateOrganization;
import com.example.EnVProtection.Models.Organization;
import com.example.EnVProtection.Services.OrganizationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/organizations")
public class OrganizationController {
    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping
    public List<Organization> getOrganizations() {
        return organizationService.getAllOrganizations();
    }
}

