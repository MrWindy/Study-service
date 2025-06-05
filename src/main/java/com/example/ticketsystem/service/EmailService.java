package com.example.ticketsystem.service;

import com.example.ticketsystem.model.Event;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException; // Изменено на jakarta
import jakarta.mail.internet.MimeMessage; // Изменено на jakarta

@Service
public class EmailService {
    private final JavaMailSender mailSender;
    private final TicketGenerator ticketGenerator;

    public EmailService(JavaMailSender mailSender, TicketGenerator ticketGenerator) {
        this.mailSender = mailSender;
        this.ticketGenerator = ticketGenerator;
    }

    public void sendTickets(String toEmail, Event event, int ticketCount, String userName) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject("Ваши билеты на " + event.getTitle());

            String content = "<h1>Ваши билеты на " + event.getTitle() + "</h1>" +
                    "<p>Спасибо за покупку! Ниже информация о вашем заказе:</p>" +
                    "<ul>" +
                    "<li><strong>Мероприятие:</strong> " + event.getTitle() + "</li>" +
                    "<li><strong>Дата:</strong> " + event.getDate() + "</li>" +
                    "<li><strong>Место:</strong> " + event.getLocation() + "</li>" +
                    "<li><strong>Количество билетов:</strong> " + ticketCount + "</li>" +
                    "</ul>" +
                    "<p>Билеты прикреплены к этому письму в виде PDF-файлов.</p>";

            helper.setText(content, true);

            for (int i = 1; i <= ticketCount; i++) {
                byte[] ticketPdf = ticketGenerator.generateTicket(event, i, userName);
                helper.addAttachment("Билет_" + i + ".pdf",
                        new ByteArrayResource(ticketPdf));
            }

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Ошибка отправки билетов", e);
        }
    }
}