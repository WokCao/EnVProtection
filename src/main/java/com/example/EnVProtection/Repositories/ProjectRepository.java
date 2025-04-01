package com.example.EnVProtection.Repositories;

import com.example.EnVProtection.Enums.ProjectStatus;
import com.example.EnVProtection.Models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByStatus(ProjectStatus status);

    List<Project> findByHostOrganizationId(Long organizationId);
}
