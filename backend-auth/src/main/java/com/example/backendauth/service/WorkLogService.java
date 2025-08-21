package com.example.backendauth.service;

import com.example.backendauth.model.WorkLog;
import java.util.List;

public interface WorkLogService {
    List<WorkLog> findAll();
}
