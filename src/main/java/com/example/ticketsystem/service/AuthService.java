package com.example.ticketsystem.service;

import com.example.ticketsystem.model.AuthResponse;
import com.example.ticketsystem.model.User;
import com.example.ticketsystem.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    public AuthResponse registerUser(String email, String password) {

        if (!isValidEmail(email)) {
            return new AuthResponse("Некорректный email", null, null);
        }
        if (userRepository.existsByEmail(email)) {
            return new AuthResponse("Email уже зарегистрирован", null, null);
        }
        if (!isPasswordStrong(password)) {
            return new AuthResponse("Пароль слишком слабый", null, null);
        }


        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user = userRepository.save(user);


        String token = generateAccessToken(user);
        return new AuthResponse("Регистрация успешна", token, user.getId());
    }

    public AuthResponse loginUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElse(null);

        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            return new AuthResponse("Неверные учетные данные", null, null);
        }


        String token = generateAccessToken(user);
        return new AuthResponse("Вход выполнен", token, user.getId());
    }

    private String generateAccessToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("userId", user.getId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24 часа
                .signWith(SignatureAlgorithm.HS256, "secret-key")
                .compact();
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