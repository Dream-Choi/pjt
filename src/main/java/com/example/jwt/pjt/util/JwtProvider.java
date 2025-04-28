package com.example.jwt.pjt.util;

import java.util.Date;


import java.security.Key;

import org.springframework.stereotype.Component;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

    private Key key =Keys.secretKeyFor(SignatureAlgorithm.HS256);
    public String generateAccToken(String email){
        System.out.println("AccessToken gen");
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*20))
                .signWith(key)
                .compact();
    }
    public String generateReToken(String email){
        System.out.println("RefreshToken gen");
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*24))
                .signWith(key)
                .compact();
    }
}
