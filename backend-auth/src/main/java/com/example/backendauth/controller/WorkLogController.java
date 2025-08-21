package com.example.backendauth.controller;

import com.example.backendauth.model.WorkLog; // Giả sử bạn có model WorkLog
import com.example.backendauth.service.WorkLogService; // Giả sử bạn có WorkLogService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class WorkLogController {

    @Autowired
    private WorkLogService workLogService;

    @GetMapping("/worklogs")
    public String showWorkLogsPage(Model model) {
        List<WorkLog> workLogs = workLogService.findAll();

        model.addAttribute("workLogs", workLogs);

        return "worklogs";
    }
}
