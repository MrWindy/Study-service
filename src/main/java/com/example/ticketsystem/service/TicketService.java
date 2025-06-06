package com.example.ticketsystem.service;

import com.example.ticketsystem.model.*;
import com.example.ticketsystem.repository.EventRepository;
import com.example.ticketsystem.repository.TicketRepository;
import com.example.ticketsystem.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TicketService {
    private final EventRepository eventRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PaymentGateway paymentGateway;

    @Autowired
    public TicketService(EventRepository eventRepository, TicketRepository ticketRepository,
                         UserRepository userRepository, EmailService emailService,
                         PaymentGateway paymentGateway) {
        this.eventRepository = eventRepository;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.paymentGateway = paymentGateway;
    }

    public List<Event> getAvailableEvents() {
        return eventRepository.findUpcomingEvents();
    }

    public boolean checkTicketsAvailable(Long eventId, int ticketCount) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Мероприятие не найдено"));
        return event.getAvailableTickets() >= ticketCount;
    }

    @Transactional
    public PaymentResponse purchaseTickets(PaymentRequest request, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));


        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(() -> new RuntimeException("Мероприятие не найдено"));


        if (event.getAvailableTickets() < request.getTicketCount()) {
            throw new RuntimeException("Доступно только " + event.getAvailableTickets() + " билетов");
        }


        validatePaymentRequest(request);


        PaymentResponse paymentResponse = paymentGateway.processPayment(
                request,
                event.getPrice() * request.getTicketCount()
        );

        if (!paymentResponse.isSuccess()) {
            return paymentResponse;
        }


        reserveTickets(event, request.getTicketCount(), user);


        emailService.sendTickets(user.getEmail(), event, request.getTicketCount(), user.getName());

        return paymentResponse;
    }

    private void validatePaymentRequest(PaymentRequest request) {
        if (request.getCardNumber() == null || request.getCardNumber().length() != 16) {
            throw new RuntimeException("Некорректный номер карты");
        }
        if (request.getCvv() == null || request.getCvv().length() != 3) {
            throw new RuntimeException("Некорректный CVV");
        }

    }

    private void reserveTickets(Event event, int count, User user) {

        for (int i = 0; i < count; i++) {
            Ticket ticket = new Ticket();
            ticket.setEvent(event);
            ticket.setUser(user);
            ticket.setPurchaseTime(LocalDateTime.now());
            ticket.setTicketNumber(UUID.randomUUID().toString());
            ticket.setRedeemed(false);
            ticketRepository.save(ticket);
        }


        event.setAvailableTickets(event.getAvailableTickets() - count);
        eventRepository.save(event);
    }
}