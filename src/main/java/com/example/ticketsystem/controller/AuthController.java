package com.example.ticketsystem.controller;

import com.example.ticketsystem.model.AuthResponse;
import com.example.ticketsystem.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;  // Добавляем зависимость


    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestParam String email,
                                 @RequestParam String password) {
        return authService.registerUser(email, password);  // Вызываем метод экземпляра
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestParam String email,
                              @RequestParam String password) {
        return authService.loginUser(email, password);  // Вызываем метод экземпляра
    }
}