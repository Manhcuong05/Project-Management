package com.example.backendauth.controller;

import com.example.backendauth.dto.EmployeeRequest;
import com.example.backendauth.model.Employee;
import com.example.backendauth.service.EmployeeService;
import com.example.backendauth.service.StorageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private StorageService storageService;

    @GetMapping
    public String listEmployees(Model model,
                                @RequestParam(value = "keyword", required = false) String keyword,
                                HttpServletRequest request) { // Thêm HttpServletRequest làm tham số
        List<Employee> employees;
        if (keyword != null && !keyword.trim().isEmpty()) {
            employees = employeeService.search(keyword);
        } else {
            employees = employeeService.findAll();
        }
        model.addAttribute("employees", employees);
        model.addAttribute("keyword", keyword);

        String requestedWithHeader = request.getHeader("X-Requested-With");
        if ("XMLHttpRequest".equals(requestedWithHeader)) {
            return "list_employee :: employee-grid";
        }

        return "list_employee";
    }

    @GetMapping("/add")
    public String showAddEmployeeForm(Model model) {
        model.addAttribute("employeeRequest", new EmployeeRequest());
        return "adduser";
    }

    @PostMapping("/add")
    public String addEmployee(@ModelAttribute("employeeRequest") EmployeeRequest employeeRequest,
                              @RequestParam("photo") MultipartFile photo) {
        String photoPath = storageService.store(photo);
        employeeRequest.setPhotoUrl(photoPath);
        employeeService.addEmployee(employeeRequest);
        return "redirect:/employees";
    }

    @GetMapping("/{id}")
    public String showEmployeeProfile(@PathVariable Long id, Model model) {
        Employee employee = employeeService.findById(id);
        EmployeeRequest employeeRequest = new EmployeeRequest();

        employeeRequest.setFirstName(employee.getFirstName());
        employeeRequest.setLastName(employee.getLastName());
        employeeRequest.setPhoneNumber(employee.getPhoneNumber());
        employeeRequest.setEmail(employee.getEmail());
        employeeRequest.setDesignation(employee.getDesignation());
        employeeRequest.setDepartment(employee.getDepartment());
        employeeRequest.setNationality(employee.getNationality());

        model.addAttribute("employee", employee);
        model.addAttribute("employeeRequest", employeeRequest);
        return "employee_profile";
    }

    @PostMapping("/{id}/edit")
    public String updateEmployee(@PathVariable Long id,
                                 @ModelAttribute("employeeRequest") EmployeeRequest employeeRequest,
                                 @RequestParam("photo") MultipartFile photo) {
        String photoPath = storageService.store(photo);
        if (photoPath != null && !photoPath.isEmpty()) {
            employeeRequest.setPhotoUrl(photoPath);
        }
        employeeService.updateEmployee(id, employeeRequest);
        return "redirect:/employees";
    }

    @PostMapping("/{id}/delete")
    public String deleteEmployee(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            employeeService.deleteEmployee(id);
            redirectAttributes.addFlashAttribute("successMessage", "Đã xóa nhân viên thành công!");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/employees";
    }
}