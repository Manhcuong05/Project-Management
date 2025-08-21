package com.example.backendauth.controller;

import com.example.backendauth.model.Employee; // ✅ Import Employee
import com.example.backendauth.service.EmployeeService; // ✅ Import EmployeeService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/profile")
    public String showProfilePage(Model model, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        String username = authentication.getName();

        Employee currentEmployee = employeeService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hồ sơ nhân viên cho người dùng: " + username));

        model.addAttribute("employeeProfile", currentEmployee);

        return "profile";
    }
}