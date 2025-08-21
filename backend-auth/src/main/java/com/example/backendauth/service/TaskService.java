package com.example.backendauth.service;

import com.example.backendauth.dto.TaskRequest;
import com.example.backendauth.model.Employee;
import com.example.backendauth.model.Project;
import com.example.backendauth.model.Task;
import com.example.backendauth.model.WorkLog;
import com.example.backendauth.repository.EmployeeRepository;
import com.example.backendauth.repository.ProjectRepository;
import com.example.backendauth.repository.TaskRepository;
import com.example.backendauth.repository.WorkLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private WorkLogRepository workLogRepository;

    public Task createTask(TaskRequest request) {
        Task task = new Task();
        task.setName(request.getName());
        task.setDescription(request.getDescription());

        if (request.getStartDate() != null) {
            task.setStartDate(Date.from(request.getStartDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        }
        if (request.getEndDate() != null) {
            task.setEndDate(Date.from(request.getEndDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        }

        task.setStatus(request.getStatus() != null ? request.getStatus() : "Pending");

        if (request.getProjectId() != null) {
            Project project = projectRepository.findById(request.getProjectId())
                    .orElseThrow(() -> new RuntimeException("Project not found"));
            task.setProject(project);
        }

        if (request.getAssigneeId() != null) {
            Employee assignee = employeeRepository.findById(request.getAssigneeId())
                    .orElseThrow(() -> new RuntimeException("Employee not found"));
            task.setAssignee(assignee);
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        task.setCreatedBy(username);

        return taskRepository.save(task);
    }

    @Transactional
    public Task completeTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!"Completed".equalsIgnoreCase(task.getStatus())) {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUsername = authentication.getName();

            Employee completer = employeeRepository.findByUsername(currentUsername)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy hồ sơ nhân viên cho người dùng: " + currentUsername));

            task.setStatus("Completed");
            task.setAssignee(completer);
            taskRepository.save(task);

            WorkLog workLog = new WorkLog();
            workLog.setEmployee(completer);
            workLog.setTask(task);
            workLog.setDate(new Date());
            workLog.setHoursWorked(2.0); // Giả định
            workLog.setDescription("Task marked as completed by " + completer.getFirstName());
            workLogRepository.save(workLog);

            return task;
        }

        return task;
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public List<Task> getTasksByProjectId(Long projectId) {
        return taskRepository.findAll().stream()
                .filter(task -> task.getProject() != null && task.getProject().getId().equals(projectId))
                .collect(Collectors.toList());
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public List<Task> search(String keyword) {
        if (keyword != null) {
            return taskRepository.search(keyword);
        }
        return taskRepository.findAll();
    }
}