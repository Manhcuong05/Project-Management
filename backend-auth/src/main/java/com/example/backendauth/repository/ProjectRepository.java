package com.example.backendauth.repository;

import com.example.backendauth.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    int countByStatus(String status);
    int countByStatusIsNull();
}
