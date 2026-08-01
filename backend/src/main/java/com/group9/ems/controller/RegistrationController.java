package com.group9.ems.controller;

import com.group9.ems.dto.AttendeeResponse;
import com.group9.ems.dto.EventResponse;
import com.group9.ems.entity.User;
import com.group9.ems.service.EventService;
import com.group9.ems.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;
    private final EventService eventService;

    @PostMapping("/api/events/{id}/register")
    public ResponseEntity<Map<String, String>> register(@PathVariable Long id,
                                                        @AuthenticationPrincipal User user) {
        registrationService.register(id, user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "Registration successful"));
    }

    @DeleteMapping("/api/events/{id}/register")
    public ResponseEntity<Map<String, String>> cancel(@PathVariable Long id,
                                                      @AuthenticationPrincipal User user) {
        registrationService.cancel(id, user);
        return ResponseEntity.ok(Map.of("message", "Registration cancelled"));
    }

    @GetMapping("/api/events/{id}/attendees")
    public List<AttendeeResponse> attendees(@PathVariable Long id,
                                            @AuthenticationPrincipal User user) {
        return registrationService.getAttendees(id, user);
    }

    @GetMapping("/api/registrations/mine")
    public List<EventResponse> myRegistrations(@AuthenticationPrincipal User user) {
        return registrationService.getMyRegistrations(user).stream()
                .map(r -> eventService.getEvent(r.getEvent().getId()))
                .toList();
    }
}