package com.example.ticketsystem.controller;

import com.example.ticketsystem.model.Event;
import com.example.ticketsystem.model.PaymentRequest;
import com.example.ticketsystem.model.PaymentResponse;
import com.example.ticketsystem.service.TicketService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/events")
    public List<Event> getAvailableEvents() {
        return ticketService.getAvailableEvents();
    }

    @PostMapping("/check")
    public boolean checkTicketsAvailability(
            @RequestParam Long eventId,
            @RequestParam int ticketCount) {
        return ticketService.checkTicketsAvailable(eventId, ticketCount);
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseTickets(
            @RequestBody PaymentRequest request,
            HttpServletRequest servletRequest) {

        Long userId = (Long) servletRequest.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Требуется авторизация");
        }

        try {
            PaymentResponse response = ticketService.purchaseTickets(request, userId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}