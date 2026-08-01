package com.group9.ems.service;

import com.group9.ems.dto.AttendeeResponse;
import com.group9.ems.entity.Event;
import com.group9.ems.entity.Registration;
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
public class RegistrationService {

    private final RegistrationRepository regRepo;
    private final EventRepository eventRepo;
    private final EmailService emailService;

    public void register(Long eventId, User user) {
        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found"));

        if (regRepo.existsByEventIdAndUserId(eventId, user.getId()))
            throw new ConflictException("You are already registered for this event"); // FR11

        if (regRepo.countByEventId(eventId) >= event.getCapacity())
            throw new ConflictException("Event is full"); // FR10

        Registration reg = new Registration();
        reg.setEvent(event);
        reg.setUser(user);
        regRepo.save(reg);

        emailService.sendConfirmation(user, event); // FR16
    }

    public void cancel(Long eventId, User user) {
        Registration reg = regRepo.findByEventIdAndUserId(eventId, user.getId())
                .orElseThrow(() -> new NotFoundException("You are not registered for this event"));
        regRepo.delete(reg); // FR12
    }

    public List<AttendeeResponse> getAttendees(Long eventId, User organizer) {
        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found"));

        if (!event.getOrganizer().getId().equals(organizer.getId()))
            throw new ConflictException("Only the event organizer can view attendees"); // NFR2

        return regRepo.findByEventId(eventId).stream()
                .map(r -> new AttendeeResponse(
                        r.getUser().getId(), r.getUser().getName(),
                        r.getUser().getEmail(), r.getRegisteredAt()))
                .toList(); // FR13
    }

    public List<Registration> getMyRegistrations(User user) {
        return regRepo.findByUser(user); // FR15 — uses your findByUser method
    }
}