package com.example.backendauth.service;

import com.example.backendauth.dto.EmployeeRequest;
import com.example.backendauth.dto.EmployeeResponse;
import com.example.backendauth.model.Employee;
import java.util.Optional;
import java.util.List;

public interface EmployeeService {

    // Phương thức mới cho Thymeleaf Controller
    List<Employee> findAll();
    List<Employee> search(String keyword);
    Employee findById(Long id);
    void deleteEmployee(Long id);
    Optional<Employee> findByEmail(String email);
    Optional<Employee> findByUsername(String username);
    List<EmployeeResponse> getAllEmployees();
    EmployeeResponse getEmployeeById(Long id);
    EmployeeResponse addEmployee(EmployeeRequest employeeRequest);
    EmployeeResponse updateEmployee(Long id, EmployeeRequest employeeRequest);
}