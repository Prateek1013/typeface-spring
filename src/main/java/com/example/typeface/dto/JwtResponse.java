package com.example.typeface.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


public class JwtResponse {
    private String token;
    private String email;
    private String username;

    public JwtResponse(String token, String email, String username) {
        this.token = token;
        this.email = email;
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

