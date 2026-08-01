package com.group9.ems.service;

import com.group9.ems.dto.EventRequest;
import com.group9.ems.dto.EventResponse;
import com.group9.ems.entity.Event;
import com.group9.ems.entity.User;
import com.group9.ems.exception.ConflictException;
import com.group9.ems.exception.NotFoundException;
import com.group9.ems.repository.EventRepository;
import com.group9.ems.repository.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepo;
    private final RegistrationRepository regRepo;

    public List<EventResponse> getAllEvents(String search) {
        List<Event> events = (search == null || search.isBlank())
                ? eventRepo.findAll()
                : eventRepo.findByTitleContainingIgnoreCase(search);
        return events.stream().map(this::toResponse).toList();
    }

    public EventResponse getEvent(Long id) {
        return toResponse(findOrThrow(id));
    }

    public EventResponse createEvent(EventRequest req, User organizer) {
        Event event = new Event();
        applyRequest(event, req);
        event.setOrganizer(organizer);
        return toResponse(eventRepo.save(event));
    }

    public EventResponse updateEvent(Long id, EventRequest req, User organizer) {
        Event event = findOrThrow(id);
        checkOwnership(event, organizer);
        applyRequest(event, req);
        return toResponse(eventRepo.save(event));
    }

    public void deleteEvent(Long id, User organizer) {
        Event event = findOrThrow(id);
        checkOwnership(event, organizer);
        eventRepo.delete(event);
    }

    public List<EventResponse> getMyEvents(User organizer) {
        return eventRepo.findByOrganizerId(organizer.getId())
                .stream().map(this::toResponse).toList();
    }

    // ---------- helpers ----------

    private Event findOrThrow(Long id) {
        return eventRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Event not found"));
    }

    private void checkOwnership(Event event, User organizer) {
        if (!event.getOrganizer().getId().equals(organizer.getId()))
            throw new ConflictException("You can only manage your own events");
    }

    private void applyRequest(Event event, EventRequest req) {
        event.setTitle(req.getTitle());
        event.setDescription(req.getDescription());
        event.setCategory(req.getCategory());
        event.setEventDate(req.getEventDate());
        event.setVenue(req.getVenue());
        event.setCapacity(req.getCapacity());
    }

    private EventResponse toResponse(Event e) {
        return new EventResponse(
                e.getId(), e.getTitle(), e.getDescription(), e.getCategory(),
                e.getEventDate(), e.getVenue(), e.getCapacity(),
                e.getOrganizer().getId(), e.getOrganizer().getName(),
                regRepo.countByEventId(e.getId())
        );
    }
}