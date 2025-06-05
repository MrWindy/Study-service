package com.example.ticketsystem.util;

import com.example.ticketsystem.model.Event;
import com.example.ticketsystem.model.User;
import com.example.ticketsystem.repository.EventRepository;
import com.example.ticketsystem.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class DataInitializer {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,
                           EventRepository eventRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        // Создание тестового пользователя
        if (userRepository.count() == 0) {
            User user = new User();
            user.setName("Илья Петров");
            user.setEmail("test@example.com");
            user.setPassword(passwordEncoder.encode("password123"));
            userRepository.save(user);
        }

        // Создание тестового мероприятия
        if (eventRepository.count() == 0) {
            Event event = new Event();
            event.setTitle("Концерт группы 'Кино'");
            event.setDate(LocalDateTime.now().plusDays(30));
            event.setLocation("Стадион 'Лужники'");
            event.setPrice(2500.0);
            event.setTotalTickets(1000);
            event.setAvailableTickets(500);
            eventRepository.save(event);
        }
    }
}