package com.example.ticketsystem.controller;

import com.example.ticketsystem.model.*;
import com.example.ticketsystem.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public PaymentResponse purchaseTickets(
            @RequestBody PaymentRequest request,
            @AuthenticationPrincipal User user) {
        return ticketService.purchaseTickets(request, user.getId());
    }
}