package com.example.backendauth.service;

import com.example.backendauth.dto.ProjectRequest;
import com.example.backendauth.model.Project;
import java.util.List;

public interface ProjectService {

    Project createProject(ProjectRequest request);

    List<Project> findAll();

    Project findById(Long id);

    Project updateProject(Long id, ProjectRequest request);

    void deleteProject(Long id);
}