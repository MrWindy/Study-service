package com.example.ticketsystem.controller;

import com.example.ticketsystem.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(@RequestParam String email,
                           @RequestParam String password) {
        return authService.registerUser(email, password);
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password) {
        return authService.loginUser(email, password);
    }

    @PostMapping("/retry")
    public String retry(@RequestParam String action,
                        @RequestParam String email,
                        @RequestParam String password) {
        if ("register".equals(action)) {
            return authService.registerUser(email, password);
        } else if ("login".equals(action)) {
            return authService.loginUser(email, password);
        }
        return "Недопустимое действие";
    }
}