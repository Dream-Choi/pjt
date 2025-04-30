package com.example.jwt.pjt.util;

import java.util.Date;
import java.nio.charset.StandardCharsets;
import java.security.Key;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.val;

@Component
public class JwtProvider {

    //private Key key =Keys.hmacShaKeyFor("your-256-bit-secret-key-which-is-long-enough-to-meet-requirements".getBytes(StandardCharsets.UTF_8));
    @Value("${jwt.secret}")
    private String secret;
    private Key getSigningKey() { 
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
    public String generateAccToken(String email){
        System.out.println("AccessToken gen");
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+1000*60))
                .signWith(getSigningKey())
                .compact();
    }
    public String generateReToken(String email){
        System.out.println("RefreshToken gen");
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*24))
                .signWith(getSigningKey())
                .compact();
    }
    public String renewToken(String token){
        System.out.println("RefreshToken gen");
        return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
    }
}
