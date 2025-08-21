package com.example.backendauth.service;

import com.example.backendauth.dto.ProjectRequest;
import com.example.backendauth.model.Project;
import com.example.backendauth.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    private void parseAndSetCreatorName(Project project) {
        String description = project.getDescription();
        if (description == null) {
            project.setCreatorName("Không rõ");
            return;
        }

        String creatorName = "Không rõ";
        int startIndex = description.indexOf("---(");
        if (startIndex != -1) {
            int endIndex = description.indexOf(")---", startIndex);
            if (endIndex != -1) {
                creatorName = description.substring(startIndex + 4, endIndex);
                project.setDescription(description.substring(0, startIndex).trim());
            }
        }
        project.setCreatorName(creatorName);
    }

    @Override
    public Project createProject(ProjectRequest request) {
        Project project = new Project();
        project.setName(request.getName());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        String descriptionWithCreator = request.getDescription() + "\n---(" + currentPrincipalName + ")---";
        project.setDescription(descriptionWithCreator);

        project.setStatus(request.getStatus() != null ? request.getStatus() : "Pending");

        if (request.getStartDate() != null) {
            project.setStartDate(request.getStartDate().atStartOfDay());
        }
        if (request.getEndDate() != null) {
            project.setEndDate(request.getEndDate().atStartOfDay());
        }

        return projectRepository.save(project);
    }

    @Override
    public List<Project> findAll() {
        List<Project> projects = projectRepository.findAll();
        projects.forEach(this::parseAndSetCreatorName);
        return projects;
    }

    @Override
    public Project findById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
        parseAndSetCreatorName(project);
        return project;
    }

    @Override
    public Project updateProject(Long id, ProjectRequest request) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));

        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setStatus(request.getStatus());

        if (request.getStartDate() != null) {
            project.setStartDate(request.getStartDate().atStartOfDay());
        }
        if (request.getEndDate() != null) {
            project.setEndDate(request.getEndDate().atStartOfDay());
        }

        return projectRepository.save(project);
    }

    @Override
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
}