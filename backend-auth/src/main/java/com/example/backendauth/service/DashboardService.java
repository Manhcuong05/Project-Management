package com.example.backendauth.service;

import com.example.backendauth.dto.DashboardData;
import com.example.backendauth.model.Project;
import com.example.backendauth.repository.ProjectRepository;
import com.example.backendauth.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

    public DashboardData getDashboardData() {
        List<Project> allProjects = projectRepository.findAll();

        long projectsCount = allProjects.size();

        List<Long> projectStatus = getProjectStatus(allProjects);
        List<Long> taskStatus = getTaskStatus();

        DashboardData data = new DashboardData();
        data.setProjectsStatus(projectStatus);
        data.setTasksStatus(taskStatus);
        data.setProjectsCount(projectsCount);

        return data;
    }

    private List<Long> getProjectStatus(List<Project> projects) {
        long completed = projects.stream().filter(p -> "Completed".equalsIgnoreCase(p.getStatus())).count();
        long inProgress = projects.stream().filter(p -> "In Progress".equalsIgnoreCase(p.getStatus())).count();
        long pending = projects.stream().filter(p -> "Pending".equalsIgnoreCase(p.getStatus())).count();

        return List.of(completed, inProgress, pending);
    }

    private List<Long> getTaskStatus() {
        long completed = taskRepository.countByStatus("Completed");
        long onHold = taskRepository.countByStatus("On Hold");
        long inProgress = taskRepository.countByStatus("In Progress");
        long pending = taskRepository.countByStatus("Pending");

        return List.of(completed, onHold, inProgress, pending);
    }
}