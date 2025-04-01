package com.example.EnVProtection.Services;

import com.example.EnVProtection.Enums.ProjectStatus;
import com.example.EnVProtection.Models.Organization;
import com.example.EnVProtection.Models.Project;
import com.example.EnVProtection.Models.User;
import com.example.EnVProtection.Repositories.OrganizationRepository;
import com.example.EnVProtection.Repositories.ProjectRepository;
import com.example.EnVProtection.Repositories.VolunteerRepository;
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

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public List<Project> getProjectsByStatus(ProjectStatus status) {
        return projectRepository.findByStatus(status);
    }

    public List<Project> getProjectsByOrganization(Long organizationId) {
        return projectRepository.findByHostOrganizationId(organizationId);
    }

    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }

    public Project createProject(Project project) {
        Organization organization = organizationRepository.findById(project.getHostOrganization().getId())
                .orElseThrow(() -> new RuntimeException("Organization not found"));
        project.setHostOrganization(organization);
        return projectRepository.save(project);
    }

    public Project updateProject(Long id, Project updatedProject) {
        Optional<Project> wrapper = getProjectById(id);

        if (wrapper.isEmpty()) return null;

        Project existingProject = wrapper.get();
        existingProject.setName(updatedProject.getName());
        existingProject.setBriefDescription(updatedProject.getBriefDescription());
        existingProject.setStatus(updatedProject.getStatus());
        return projectRepository.save(existingProject);
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    public Project joinProject(Long projectId, User volunteer) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        project.getVolunteers().add(volunteer);
        return projectRepository.save(project);
    }
}

