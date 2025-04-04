package com.example.EnVProtection.Services;

import com.example.EnVProtection.Enums.ProjectStatus;
import com.example.EnVProtection.Models.Organization;
import com.example.EnVProtection.Models.Project;
import com.example.EnVProtection.Models.Volunteer;
import com.example.EnVProtection.Repositories.OrganizationRepository;
import com.example.EnVProtection.Repositories.ProjectRepository;
import com.example.EnVProtection.Repositories.VolunteerRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final OrganizationRepository organizationRepository;
    private final VolunteerRepository volunteerRepository;

    public ProjectService(ProjectRepository projectRepository, OrganizationRepository organizationRepository, VolunteerRepository volunteerRepository) {
        this.projectRepository = projectRepository;
        this.organizationRepository = organizationRepository;
        this.volunteerRepository = volunteerRepository;
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public List<Project> getProjectsByStatus(ProjectStatus status) {
        return projectRepository.findByStatus(status);
    }

    public List<Project> getProjectsByOrganization(Long organizationId) {
        return sortProjects(projectRepository.findByHostOrganizationId(organizationId));
    }

    private List<Project> sortProjects(List<Project> projectList) {
        return projectList.stream()
                .sorted(Comparator.comparing(Project::getStatus, Comparator.comparingInt(status -> {
                            if (status == ProjectStatus.IN_PROGRESS) return 0;
                            if (status == ProjectStatus.UPCOMING) return 1;
                            return 2;
                        }))
                        .thenComparing(Project::getDate)
                        .thenComparing(Project::getTime)
                        .thenComparing(Comparator.comparingLong(Project::getCurrentNumberVolunteers).reversed()))
                .collect(Collectors.toList());
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
        existingProject.setFullDescription(updatedProject.getFullDescription());
        existingProject.setImpact(updatedProject.getImpact());
        existingProject.setLocation(updatedProject.getLocation());
        existingProject.setLatitude(updatedProject.getLatitude());
        existingProject.setLongitude(updatedProject.getLongitude());
        existingProject.setDate(updatedProject.getDate());
        existingProject.setTime(updatedProject.getTime());
        existingProject.setRequirements(updatedProject.getRequirements());
        existingProject.setVolunteersNeeded(updatedProject.getVolunteersNeeded());
        existingProject.setImage(updatedProject.getImage());
        return projectRepository.save(existingProject);
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    public Project joinProject(Long projectId, String email) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        Volunteer volunteer = volunteerRepository.findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("Volunteer not found"));
        project.getVolunteers().add(volunteer);
        return projectRepository.save(project);
    }

    public Project quitProject(Long projectId, String email) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        Volunteer volunteer = volunteerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Volunteer not found"));
        if (project.getVolunteers().contains(volunteer)) {
            project.getVolunteers().remove(volunteer);
        } else {
            throw new RuntimeException("Volunteer not enrolled in the project");
        }
        return projectRepository.save(project);
    }
}

