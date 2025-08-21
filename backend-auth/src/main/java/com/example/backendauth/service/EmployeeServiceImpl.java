package com.example.backendauth.service;

import com.example.backendauth.dto.EmployeeRequest;
import com.example.backendauth.dto.EmployeeResponse;
import com.example.backendauth.model.Employee;
import com.example.backendauth.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import com.example.backendauth.repository.TaskRepository;



import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TaskRepository taskRepository;


    @Override
    public Employee findById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
    }

    @Override
    @Transactional
    public void deleteEmployee(Long id) {
        Employee employeeToDelete = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));

        long taskCount = taskRepository.countByAssignee(employeeToDelete);
        if (taskCount > 0) {
            throw new IllegalStateException("Không thể xóa nhân viên này vì còn " + taskCount + " công việc chưa hoàn thành.");
        }

        employeeRepository.delete(employeeToDelete);
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeResponse getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        return mapToResponse(employee);
    }

    @Override
    public EmployeeResponse addEmployee(EmployeeRequest employeeRequest) {
        Employee employee = new Employee();
        employee.setFirstName(employeeRequest.getFirstName());
        employee.setLastName(employeeRequest.getLastName());
        employee.setPhoneNumber(employeeRequest.getPhoneNumber());
        employee.setEmail(employeeRequest.getEmail());
        employee.setDesignation(employeeRequest.getDesignation());
        employee.setDepartment(employeeRequest.getDepartment());
        employee.setCreatedBy(employeeRequest.getCreatedBy());
        employee.setNationality(employeeRequest.getNationality());
        employee.setPhotoUrl(employeeRequest.getPhotoUrl());
        employee.setCreatedDate(employeeRequest.getCreatedDate() != null ? employeeRequest.getCreatedDate() : LocalDate.now());

        Employee savedEmployee = employeeRepository.save(employee);
        return mapToResponse(savedEmployee);
    }

    @Override
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest employeeRequest) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        employee.setFirstName(employeeRequest.getFirstName());
        employee.setLastName(employeeRequest.getLastName());
        employee.setPhoneNumber(employeeRequest.getPhoneNumber());
        employee.setEmail(employeeRequest.getEmail());
        employee.setDesignation(employeeRequest.getDesignation());
        employee.setDepartment(employeeRequest.getDepartment());
        employee.setNationality(employeeRequest.getNationality());
        employee.setPhotoUrl(employeeRequest.getPhotoUrl());

        Employee updatedEmployee = employeeRepository.save(employee);
        return mapToResponse(updatedEmployee);
    }

    private EmployeeResponse mapToResponse(Employee employee) {
        EmployeeResponse response = new EmployeeResponse();
        response.setId(employee.getId());
        response.setFirstName(employee.getFirstName());
        response.setLastName(employee.getLastName());
        response.setPhoneNumber(employee.getPhoneNumber());
        response.setEmail(employee.getEmail());
        response.setDesignation(employee.getDesignation());
        response.setDepartment(employee.getDepartment());
        response.setCreatedBy(employee.getCreatedBy());
        response.setCreatedDate(employee.getCreatedDate());
        return response;
    }

    @Override
    public List<Employee> search(String keyword) {
        return employeeRepository.search(keyword);
    }

    @Override
    public Optional<Employee> findByEmail(String email) {
        return employeeRepository.findByUsername(email);
    }

    @Override
    public Optional<Employee> findByUsername(String username) {
        return employeeRepository.findByUsername(username);
    }
}