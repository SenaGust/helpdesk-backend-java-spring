package com.senagust.helpdesk.service.impl;

import com.senagust.helpdesk.service.IJwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService implements IJwtService {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration-in-ms}")
    private Long expirationInMs;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return Jwts
                .builder()
                .subject(userDetails.getUsername())
                .claim("roles", userDetails.getAuthorities())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationInMs))
                .signWith(getSigningKey())
                .compact();
    }

    @Override
    public String extractUserId(String token) {
        return (parseClaims(token).getSubject());
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        String userId = extractUserId(token);
        Date expiration = parseClaims(token).getExpiration();

        boolean isNotExpired = expiration.after(new Date());

        String storedUserId = userDetails.getUsername();
        boolean isSameUserID = userId.equals(storedUserId);

        return isNotExpired && isSameUserID;
    }
}
