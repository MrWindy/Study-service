package com.example.ticketsystem.service;

import com.example.ticketsystem.model.Event;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class TicketGenerator {

    public byte[] generateTicket(Event event, int ticketNumber, String userName) {
        try (PDDocument document = new PDDocument();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Заголовок
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("Билет на мероприятие");
                contentStream.endText();


                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(100, 650);
                contentStream.showText("Мероприятие: " + event.getTitle());
                contentStream.newLineAtOffset(0, -25);
                contentStream.showText("Дата: " + event.getDate());
                contentStream.newLineAtOffset(0, -25);
                contentStream.showText("Место: " + event.getLocation());
                contentStream.newLineAtOffset(0, -25);
                contentStream.showText("Билет №: " + ticketNumber);
                contentStream.newLineAtOffset(0, -25);
                contentStream.showText("Владелец: " + userName);
                contentStream.endText();


                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 10);
                contentStream.newLineAtOffset(100, 500);
                contentStream.showText("[QR-код будет здесь]");
                contentStream.endText();
            }

            document.save(out);
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка генерации билета", e);
        }
    }
}