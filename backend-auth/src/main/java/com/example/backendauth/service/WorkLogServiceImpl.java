package com.example.backendauth.service;

import com.example.backendauth.model.WorkLog;
import com.example.backendauth.repository.WorkLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkLogServiceImpl implements WorkLogService {

    @Autowired
    private WorkLogRepository workLogRepository;

    @Override
    public List<WorkLog> findAll() {
        return workLogRepository.findAll(org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC, "date"));
    }
}
