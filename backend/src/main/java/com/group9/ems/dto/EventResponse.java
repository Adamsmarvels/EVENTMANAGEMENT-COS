package com.group9.ems.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class EventResponse {
    private Long id;
    private String title;
    private String description;
    private String category;
    private LocalDateTime eventDate;
    private String venue;
    private Integer capacity;
    private Long organizerId;
    private String organizerName;
    private Long registeredCount;
}