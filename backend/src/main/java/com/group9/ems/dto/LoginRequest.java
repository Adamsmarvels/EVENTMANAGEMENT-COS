package com.group9.ems.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}