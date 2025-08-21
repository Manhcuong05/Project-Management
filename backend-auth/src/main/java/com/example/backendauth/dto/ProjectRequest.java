package com.example.backendauth.dto;

import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate; // SỬ DỤNG LocalDate

public class ProjectRequest {
    private String name;
    private String description;
    private String status;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate; // ĐỔI THÀNH LocalDate

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate; // ĐỔI THÀNH LocalDate

    // getters/setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
}