package com.task.manager.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.security.Key;
@ConfigurationProperties
@Component
public class JwtUtil {

    private final String SECRET;
    private final Key key;

    public JwtUtil(@Value("${jwt.secret}") String SECRET) {
        this.SECRET = SECRET;
        this.key = Keys.hmacShaKeyFor(SECRET.getBytes());
    }
    public String generateToken(Long id, String username, String role){
        return Jwts.builder()
                .setSubject(username)              // sub
                .claim("id", id)                   // user id
                .claim("role", role)               // role
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(key)
                .compact();
    }

    public Claims extractClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token){
        return extractClaims(token).getSubject();
    }

    public Long extractUserId(String token){
        return extractClaims(token).get("id", Long.class);
    }

    public String extractRole(String token){
        return extractClaims(token).get("role", String.class);
    }
}

