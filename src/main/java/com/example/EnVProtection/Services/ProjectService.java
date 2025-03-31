package com.example.EnVProtection.Services;

import com.example.EnVProtection.Enums.ProjectStatus;
import com.example.EnVProtection.Models.Project;
import com.example.EnVProtection.Models.User;
import com.example.EnVProtection.Repositories.OrganizationRepository;
import com.example.EnVProtection.Repositories.ProjectRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final OrganizationRepository organizationRepository;

    public ProjectService(ProjectRepository projectRepository, OrganizationRepository organizationRepository) {
        this.projectRepository = projectRepository;
        this.organizationRepository = organizationRepository;
    }

    public List<Project> getAllProjects(ProjectStatus status) {
        return projectRepository.findByStatus(status);
    }

    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public Project joinProject(Long projectId, User volunteer) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        project.getVolunteers().add(volunteer);
        return projectRepository.save(project);
    }
}

