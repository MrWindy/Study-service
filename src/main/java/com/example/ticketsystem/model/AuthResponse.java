package com.example.ticketsystem.model;

public class AuthResponse {
    private String message;
    private String token;
    private Long userId;

    public AuthResponse() {}

    public AuthResponse(String message, String token, Long userId) {
        this.message = message;
        this.token = token;
        this.userId = userId;
    }


    public String getMessage() { return message; }
    public String getToken() { return token; }
    public Long getUserId() { return userId; }

    public void setMessage(String message) { this.message = message; }
    public void setToken(String token) { this.token = token; }
    public void setUserId(Long userId) { this.userId = userId; }
}