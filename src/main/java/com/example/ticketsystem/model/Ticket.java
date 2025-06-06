package com.example.ticketsystem.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ticketNumber;
    private LocalDateTime purchaseTime;
    private boolean redeemed;

    @ManyToOne
    private Event event;

    @ManyToOne
    @JoinColumn(name = "user_id") // Явно укажите имя столбца
    private User user;


    public Long getId() {
        return id;
    }
    public User getUser() {
        return user;
    }
    public Event getEvent() {
        return event;
    }
    public boolean isRedeemed() {
        return redeemed;
    }
    public String getTicketNumber() {
        return ticketNumber;
    }
    public LocalDateTime getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(LocalDateTime purchaseTime) {
        this.purchaseTime = purchaseTime;
    }
    public void setRedeemed(boolean redeemed) {
        this.redeemed = redeemed;
    }
    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }
    public void setEvent(Event event) {
        this.event = event;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setUser(User user) {
        this.user = user;
    }
}