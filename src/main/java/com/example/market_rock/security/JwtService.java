package com.example.market_rock.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private final String SECRET =
            "my-super-secret-key-for-market-rock-project-123456";

    private final SecretKey key =
            Keys.hmacShaKeyFor(SECRET.getBytes());

    public String generateToken(CustomUserDetails user) {

        return Jwts.builder()
                .subject(user.getUid())
                .claim("role", user.getRole())
                .claim("email", user.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(key)
                .compact();
    }
}