package com.example.backendauth.controller;

import com.example.backendauth.dto.RegisterRequest; // Đảm bảo dùng đúng DTO của bạn
import com.example.backendauth.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {

        model.addAttribute("userDto", new RegisterRequest());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute("userDto") @Valid RegisterRequest userDto,
                                      BindingResult result, Model model) {

        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {

            result.rejectValue("confirmPassword", "error.userDto", "Mật khẩu không khớp");
        }

        if (result.hasErrors()) {
            return "register";
        }


        try {
            userService.register(userDto);
        } catch (RuntimeException e) {
            result.rejectValue("username", "error.userDto", e.getMessage());
            return "register";
        }

        return "redirect:/login?success";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
}