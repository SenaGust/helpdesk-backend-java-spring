package com.senagust.helpdesk.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface IJwtService {
    String generateToken(UserDetails userDetails);

    String extractUserId(String token);

    boolean isTokenValid(String token, UserDetails userDetails);
}
