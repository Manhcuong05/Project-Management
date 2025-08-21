package com.example.backendauth.repository;

import com.example.backendauth.model.SelectedProject;
import com.example.backendauth.model.User;
import com.example.backendauth.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SelectedProjectRepository extends JpaRepository<SelectedProject, Long> {
    List<SelectedProject> findByUser(User user);
    List<SelectedProject> findByProject(Project project);
    boolean existsByUserAndProject(User user, Project project);
}
