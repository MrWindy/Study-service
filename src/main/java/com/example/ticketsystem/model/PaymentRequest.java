package com.example.ticketsystem.model;

public class PaymentRequest {
    private Long eventId;
    private int ticketCount;
    private String cardNumber;
    private String cardHolder;
    private String expiryDate;
    private String cvv;

    public Long getEventId() {
        return eventId;
    }
    public String getCvv() {return cvv;}
    public String getCardNumber() {
        return cardNumber;
    }
    public int getTicketCount() {
        return ticketCount;
    }
    public String getExpiryDate() {
        return expiryDate;
    }
    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
    public void setTicketCount(int ticketCount) {
        this.ticketCount = ticketCount;
    }
    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
    public void setCvv(String cvv) { this.cvv = cvv; }
}

