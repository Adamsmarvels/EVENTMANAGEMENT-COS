package com.group9.ems.repository;

import com.group9.ems.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByOrganizerId(Long organizerId);
    List<Event> findByTitleContainingIgnoreCase(String search);
    List<Event> findByCategoryIgnoreCase(String category);
}