package com.example.backendauth.dto;

public class AuthResponse {

    private String token;
    private String role;

    // Constructor
    public AuthResponse(String token, String role) {
        this.token = token;
        this.role = role;
    }

    // Getter và Setter cho token
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    // Getter và Setter cho role
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Optional: Override toString() method để dễ dàng in thông tin trong log
    @Override
    public String toString() {
        return "AuthResponse{" +
                "token='" + token + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
