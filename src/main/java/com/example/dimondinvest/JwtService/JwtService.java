package com.example.dimondinvest.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    // Fixed key (har server restart pe same rahe)
    private final Key key = Keys.hmacShaKeyFor(
            "my_super_secret_key_which_is_long_enough_32bytes!".getBytes()
    );

    private final long JWT_EXPIRATION_MS = 3600000; // 1 hour

    // -----------------------------
    // Token generate
    // -----------------------------
    public String generateToken(Long number, String roles) {
        return Jwts.builder()
                .setSubject(String.valueOf(number)) // number as string
                .claim("roles", roles)              // roles claim
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_MS))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // -----------------------------
    // Extract number from token
    // -----------------------------
    public Long extractNumber(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token.trim())
                .getBody();
        return Long.parseLong(claims.getSubject()); // subject = number
    }

    // -----------------------------
    // Extract roles from token
    // -----------------------------
    public String extractRoles(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token.trim())
                .getBody();
        return claims.get("roles", String.class);
    }

    // -----------------------------
    // Validate token
    // -----------------------------
    public boolean isTokenValid(String token, UserDetails userDetails) {
        Long numberFromToken = extractNumber(token);
        return numberFromToken.equals(Long.parseLong(userDetails.getUsername()))
                && !isTokenExpired(token);
    }

    // -----------------------------
    // Check expiration
    // -----------------------------
    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token.trim())
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }
}
