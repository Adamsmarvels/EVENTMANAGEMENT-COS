package com.group9.ems.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AttendeeResponse {
    private Long userId;
    private String name;
    private String email;
    private LocalDateTime registeredAt;
}