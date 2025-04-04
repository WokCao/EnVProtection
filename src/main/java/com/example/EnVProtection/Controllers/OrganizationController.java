package com.example.EnVProtection.Controllers;

import com.example.EnVProtection.Enums.ProjectStatus;
import com.example.EnVProtection.Models.Organization;
import com.example.EnVProtection.Models.Project;
import com.example.EnVProtection.Services.OrganizationService;
import com.example.EnVProtection.Services.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/organizations")
public class OrganizationController {
    private final OrganizationService organizationService;
    private final ProjectService projectService;

    public OrganizationController(OrganizationService organizationService, ProjectService projectService) {
        this.organizationService = organizationService;
        this.projectService = projectService;
    }

    @GetMapping
    public ResponseEntity<?> getOrganizations() {
        List<Organization> list = organizationService.getAllOrganizations();
        List<Map<String, Object>> responseList = new ArrayList<>();

        for (Organization org : list) {
            List<Project> projectList = projectService.getProjectsByOrganization(org.getId());
            final Long[] sum = {0L};
            projectList.forEach((project -> {
                if (project.getStatus() != ProjectStatus.DONE) {
                    sum[0] += project.getVolunteers().size();
                }
            }));

            Map<String, Object> orgData = new HashMap<>();
            orgData.put("organization", org);
            orgData.put("projectCount", projectList.size());
            orgData.put("volunteers", sum[0]);

            responseList.add(orgData);
        }
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrganizationById(@PathVariable("id") Long id) {
        Optional<Organization> optionalOrganization = organizationService.getOrganizationById(id);

        if (optionalOrganization.isEmpty()) return ResponseEntity.notFound().build();

        Map<String, Object> map = new HashMap<>();
        Organization organization = optionalOrganization.get();

        List<Project> projectList = projectService.getProjectsByOrganization(organization.getId());
        final Long[] sum = {0L};
        projectList.forEach((project -> {
            if (project.getStatus() != ProjectStatus.DONE) {
                sum[0] += project.getVolunteers().size();
            }
        }));

        Long activeProjects = projectList.stream()
                .filter(p -> p.getStatus() == ProjectStatus.IN_PROGRESS || p.getStatus() == ProjectStatus.UPCOMING)
                .count();

        map.put("organization", organization);
        map.put("activeProjects", activeProjects);
        map.put("project", projectList.subList(0, 4));
        map.put("volunteers", sum[0]);

        return ResponseEntity.ok(map);
    }

    @GetMapping("/{id}/projects")
    public ResponseEntity<?> getOrganizationProjects(@PathVariable("id") Long id) {
        Optional<Organization> optionalOrganization = organizationService.getOrganizationById(id);

        if (optionalOrganization.isEmpty()) return ResponseEntity.notFound().build();

        Map<String, Object> map = new HashMap<>();
        Organization organization = optionalOrganization.get();

        List<Project> projectList = projectService.getProjectsByOrganization(organization.getId());
        map.put("organization", organization);
        map.put("projects", projectList);

        return ResponseEntity.ok(map);
    }
}

