package com.example.backendauth.controller;

import com.example.backendauth.model.User;
import com.example.backendauth.service.DashboardService;
import com.example.backendauth.dto.DashboardData;
import com.example.backendauth.service.EmployeeService; // ✅ 1. Thêm import cho EmployeeService
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import com.example.backendauth.model.Employee;


@Controller
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmployeeService employeeService; // ✅ 2. Tiêm (Inject) EmployeeService

    @GetMapping("/dashboard")
    public String showDashboard(Model model, Authentication authentication) throws JsonProcessingException {

        DashboardData dashboardData = dashboardService.getDashboardData();

        String chartDataJson = objectMapper.writeValueAsString(dashboardData);
        model.addAttribute("chartDataJson", chartDataJson);

        model.addAttribute("projectsCount", dashboardData.getProjectsCount());

        long employeesCount = employeeService.findAll().size();
        model.addAttribute("employeesCount", employeesCount);

        if (authentication != null) {
            model.addAttribute("user", authentication.getPrincipal());
        }

        return "dashboard";
    }
    @GetMapping("/list_employee")
    public String listEmployees(Model model) {
        List<Employee> employees = employeeService.findAll();
        model.addAttribute("employees", employees);
        return "list_employee";
    }
}