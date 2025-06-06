package com.example.ticketsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private LocalDateTime date;
    private String location;
    private double price;
    private int totalTickets;
    private int availableTickets;


    public Long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public String getLocation() {
        return location;
    }
    public double getPrice() {
        return price;
    }
    public int getTotalTickets() {
        return totalTickets;
    }
    public int getAvailableTickets() {
        return availableTickets;
    }

    public void setAvailableTickets(int availableTickets) {
        this.availableTickets = availableTickets;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

}