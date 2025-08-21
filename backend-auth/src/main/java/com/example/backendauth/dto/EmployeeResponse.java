package com.example.backendauth.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Pattern;



public class EmployeeResponse {
    private Long id;
    private String firstName;
    private String lastName;// Giữ LocalDate ở đây
    private String designation;
    private String department;
    private String createdBy;
    private String email;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate;

    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be exactly 10 digits")
    private String phoneNumber;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getCreatedDate() {
        return createdDate;  // Trả về LocalDate
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;  // Gán giá trị LocalDate
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }



    // Trả về formatted string của createdDate (chuyển LocalDate thành String)
    public String getFormattedCreatedDate() {
        if (createdDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return createdDate.format(formatter);  // Chuyển đổi LocalDate thành String
        }
        return null;  // Nếu createdDate là null, trả về null
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
