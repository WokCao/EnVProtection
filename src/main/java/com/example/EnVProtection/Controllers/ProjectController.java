package com.example.EnVProtection.Controllers;

import com.example.EnVProtection.Enums.ProjectStatus;
import com.example.EnVProtection.Enums.Role;
import com.example.EnVProtection.Models.Project;
import com.example.EnVProtection.Models.User;
import com.example.EnVProtection.Repositories.UserRepository;
import com.example.EnVProtection.Services.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;
    private final UserRepository userRepository;

    public ProjectController(ProjectService projectService, UserRepository userRepository) {
        this.projectService = projectService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<Project> getProjects(@RequestParam(required = false) ProjectStatus status) {
        return projectService.getAllProjects(status);
    }

    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody Project project) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

//        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
//        }

//        Object principal = auth.getPrincipal();
//
//        String email;
//        if (principal instanceof String) {
//            email = (String) principal;
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid authentication principal");
//        }
//
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
//
//        if (user.getRole() != Role.OWNER) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only owners can create projects");
//        }

        Project savedProject = projectService.createProject(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProject);
    }


    @PostMapping("/{id}/join")
    public Project joinProject(@PathVariable Long id, @RequestBody User user) {
        return projectService.joinProject(id, user);
    }
}

