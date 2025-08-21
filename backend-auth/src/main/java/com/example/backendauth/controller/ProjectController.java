package com.example.backendauth.controller;

import com.example.backendauth.model.Project;
import com.example.backendauth.dto.ProjectRequest;
import com.example.backendauth.service.ProjectService;
import com.example.backendauth.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.backendauth.model.Task;


import java.util.List;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TaskService taskService;

    @GetMapping
    public String listProjects(Model model) {
        List<Project> projects = projectService.findAll();
        model.addAttribute("projects", projects);
        return "projects";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("projectRequest", new ProjectRequest());
        return "create_project";
    }

    @PostMapping("/create")
    public String createProject(@ModelAttribute("projectRequest") ProjectRequest request) {
        projectService.createProject(request);
        return "redirect:/projects";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Project project = projectService.findById(id);

        ProjectRequest projectRequest = new ProjectRequest();
        projectRequest.setName(project.getName());
        projectRequest.setDescription(project.getDescription());
        projectRequest.setStatus(project.getStatus());
        if (project.getStartDate() != null) {
            projectRequest.setStartDate(project.getStartDate().toLocalDate());
        }
        if (project.getEndDate() != null) {
            projectRequest.setEndDate(project.getEndDate().toLocalDate());
        }

        model.addAttribute("projectRequest", projectRequest);
        model.addAttribute("projectId", id);

        return "edit_project";
    }

    @PostMapping("/{id}/edit")
    public String processEditForm(@PathVariable Long id, @ModelAttribute("projectRequest") ProjectRequest request) {
        projectService.updateProject(id, request);
        return "redirect:/projects";
    }

    @GetMapping("/{projectId}/tasks")
    public String viewProjectTasks(@PathVariable Long projectId, Model model) {
        Project project = projectService.findById(projectId);
        List<Task> tasks = taskService.getTasksByProjectId(projectId);
        model.addAttribute("project", project);
        model.addAttribute("tasks", tasks);
        return "task";
    }

    @PostMapping("/{id}/delete")
    public String deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return "redirect:/projects";
    }
}