package com.group9.ems.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EventRequest {
    private String title;
    private String description;
    private String category;
    private LocalDateTime eventDate;
    private String venue;
    private Integer capacity;
}