package com.group9.ems.controller;

import com.group9.ems.dto.EventRequest;
import com.group9.ems.dto.EventResponse;
import com.group9.ems.entity.User;
import com.group9.ems.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping
    public List<EventResponse> getAll(@RequestParam(required = false) String search) {
        return eventService.getAllEvents(search);
    }

    @GetMapping("/mine")
    public List<EventResponse> myEvents(@AuthenticationPrincipal User user) {
        return eventService.getMyEvents(user);
    }

    @GetMapping("/{id}")
    public EventResponse getOne(@PathVariable Long id) {
        return eventService.getEvent(id);
    }

    @PostMapping
    public ResponseEntity<EventResponse> create(@RequestBody EventRequest req,
                                                @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(eventService.createEvent(req, user));
    }

    @PutMapping("/{id}")
    public EventResponse update(@PathVariable Long id,
                                @RequestBody EventRequest req,
                                @AuthenticationPrincipal User user) {
        return eventService.updateEvent(id, req, user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @AuthenticationPrincipal User user) {
        eventService.deleteEvent(id, user);
        return ResponseEntity.noContent().build();
    }
}