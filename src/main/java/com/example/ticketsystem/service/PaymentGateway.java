package com.example.ticketsystem.service;

import com.example.ticketsystem.model.PaymentRequest;
import com.example.ticketsystem.model.PaymentResponse;
import org.springframework.stereotype.Service;
import java.time.YearMonth;
import java.util.UUID;

@Service
public class PaymentGateway {
    public PaymentResponse processPayment(PaymentRequest request, double amount) {
        if (isCardExpired(request.getExpiryDate())) {
            return new PaymentResponse(false, null, "Срок действия карты истек");
        }

        if (request.getCardNumber().endsWith("1111")) {
            return new PaymentResponse(false, null, "Недостаточно средств");
        }

        String transactionId = "TX" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return new PaymentResponse(true, transactionId, null);
    }

    private boolean isCardExpired(String expiryDate) {
        try {
            String[] parts = expiryDate.split("/");
            int month = Integer.parseInt(parts[0]);
            int year = 2000 + Integer.parseInt(parts[1]);
            YearMonth cardDate = YearMonth.of(year, month);
            return cardDate.isBefore(YearMonth.now());
        } catch (Exception e) {
            return true;
        }
    }
}