package com.example.ticketsystem.repository;

import com.example.ticketsystem.model.Event;
import com.example.ticketsystem.model.Ticket;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    int countByEventAndUserIsNull(Event event);
    List<Ticket> findByEventAndUserIsNull(Event event, Pageable pageable);
}