package com.example.ticketsystem.repository;

import com.example.ticketsystem.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("SELECT e FROM Event e WHERE e.date > CURRENT_TIMESTAMP ORDER BY e.date ASC")
    List<Event> findUpcomingEvents();
}