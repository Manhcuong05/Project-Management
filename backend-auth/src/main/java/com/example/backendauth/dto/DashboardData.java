package com.example.backendauth.dto;

import java.util.List;

public class DashboardData {

    private List<Long> projectsStatus;
    private List<Long> tasksStatus;
    private List<Long> workLogStatus;
    private List<Long> performanceStatus;
    private long projectsCount;


    public List<Long> getProjectsStatus() {
        return projectsStatus;
    }

    public void setProjectsStatus(List<Long> projectsStatus) {
        this.projectsStatus = projectsStatus;
    }

    public List<Long> getTasksStatus() {
        return tasksStatus;
    }

    public void setTasksStatus(List<Long> tasksStatus) {
        this.tasksStatus = tasksStatus;
    }

    public List<Long> getWorkLogStatus() {
        return workLogStatus;
    }

    public void setWorkLogStatus(List<Long> workLogStatus) {
        this.workLogStatus = workLogStatus;
    }

    public List<Long> getPerformanceStatus() {
        return performanceStatus;
    }

    public void setPerformanceStatus(List<Long> performanceStatus) {
        this.performanceStatus = performanceStatus;
    }

    public long getProjectsCount() {
        return projectsCount;
    }

    public void setProjectsCount(long projectsCount) {
        this.projectsCount = projectsCount;
    }
}