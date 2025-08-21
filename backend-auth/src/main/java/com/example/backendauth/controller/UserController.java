package com.example.backendauth.controller;

import com.example.backendauth.model.User;
import com.example.backendauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin/add-user")
    public String addUserPage() {
        return "adduser";
    }

    @PostMapping("/admin/add-user")
    public String addUser(@ModelAttribute User user) {
        return "redirect:/admin/users";
    }
}