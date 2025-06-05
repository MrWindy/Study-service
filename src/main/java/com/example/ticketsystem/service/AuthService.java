package com.example.ticketsystem.service;

import com.example.ticketsystem.model.User;
import com.example.ticketsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String registerUser(String email, String password) {
        if (!isValidEmail(email)) {
            return "Email некорректен";
        }

        if (userRepository.existsByEmail(email)) {
            return "Пользователь с таким Email уже зарегистрирован";
        }

        if (!isPasswordStrong(password)) {
            return "Пароль слишком легкий";
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        return "Регистрация успешна";
    }

    public String loginUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElse(null);

        if (user == null) {
            return "Пользователь не найден";
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return "Неверный пароль";
        }

        return "Вход выполнен успешно";
    }

    private boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        final Pattern pattern = Pattern.compile(EMAIL_REGEX);

        if (!pattern.matcher(email).matches()) {
            return false;
        }

        String[] parts = email.split("@");
        if (parts.length != 2) {
            return false;
        }

        String domain = parts[1];
        return !domain.isEmpty() && !domain.startsWith(".") && !domain.endsWith(".");
    }

    private boolean isPasswordStrong(String password) {
        if (password == null) {
            return false;
        }

        if (password.length() < 8) {
            return false;
        }

        if (!password.matches(".*\\d.*")) {
            return false;
        }

        if (!password.matches(".*[A-Z].*")) {
            return false;
        }

        return password.matches(".*[a-z].*");
    }
}