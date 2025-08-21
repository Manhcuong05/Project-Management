package com.example.backendauth.controller;

import com.example.backendauth.dto.TaskRequest;
import com.example.backendauth.model.Employee;
import com.example.backendauth.model.Project;
import com.example.backendauth.model.Task;
import com.example.backendauth.service.EmployeeService;
import com.example.backendauth.service.ProjectService;
import com.example.backendauth.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ProjectService projectService;

    @GetMapping("/tasks")
    public String listAllTasksByProject(Model model) {
        List<Project> projects = projectService.findAll();
        List<Task> allTasks = taskService.findAll();

        Map<Long, List<Task>> tasksByProjectId = allTasks.stream()
                .filter(task -> task.getProject() != null)
                .collect(Collectors.groupingBy(task -> task.getProject().getId()));

        model.addAttribute("projects", projects);
        model.addAttribute("projectTasks", tasksByProjectId);

        return "list_task";
    }

    @GetMapping("/projects/{projectId}/tasks/create")
    public String showCreateTaskForm(@PathVariable Long projectId, Model model) {
        Project project = projectService.findById(projectId);
        model.addAttribute("project", project);

        List<Employee> employees = employeeService.findAll();
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setProjectId(projectId);

        model.addAttribute("taskRequest", taskRequest);
        model.addAttribute("allEmployees", employees);

        return "create_task";
    }

    @PostMapping("/projects/{projectId}/tasks/create")
    public String createTask(@PathVariable Long projectId, @ModelAttribute("taskRequest") TaskRequest request) {
        taskService.createTask(request);
        return "redirect:/projects/" + projectId + "/tasks";
    }

    @PostMapping("/tasks/{id}/complete")
    public String completeTask(@PathVariable Long id, @RequestParam Long projectId) {
        taskService.completeTask(id);
        return "redirect:/worklogs";
    }

    @PostMapping("/tasks/{id}/delete")
    public String deleteTask(@PathVariable Long id, @RequestParam Long projectId) {
        taskService.deleteTask(id);
        return "redirect:/projects/" + projectId + "/tasks";
    }
}
