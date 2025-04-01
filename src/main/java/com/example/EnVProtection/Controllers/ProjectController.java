package com.example.EnVProtection.Controllers;

import com.example.EnVProtection.Enums.ProjectStatus;
import com.example.EnVProtection.Models.Project;
import com.example.EnVProtection.Models.User;
import com.example.EnVProtection.Repositories.VolunteerRepository;
import com.example.EnVProtection.Services.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/status/{status}")
    public List<Project> getProjects(@PathVariable ProjectStatus status) {
        return projectService.getProjectsByStatus(status);
    }

    @GetMapping("/organization/{organizationId}")
    public List<Project> getProjectsByOrganization(@PathVariable Long organizationId) {
        return projectService.getProjectsByOrganization(organizationId);
    }

    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody Project project) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
//        }
//
//        Object principal = auth.getPrincipal();
//
//        String email;
//        if (principal instanceof String) {
//            email = (String) principal;
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid authentication principal");
//        }

//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
//
//        if (user.getRole() != Role.ORGANIZATION) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only organizations can create projects");
//        }

        Project savedProject = projectService.createProject(project);
        System.out.println(savedProject);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProject);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project updatedProject) {
        Project project = projectService.updateProject(id, updatedProject);
        return ResponseEntity.ok(project);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/join")
    public Project joinProject(@PathVariable Long id, @RequestBody User user) {
        return projectService.joinProject(id, user);
    }
}

