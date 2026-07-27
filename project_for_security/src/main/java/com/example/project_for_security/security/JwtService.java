package com.example.project_for_security.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    public String generateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+expiration))
                .signWith(getsigningKey(),SignatureAlgorithm.HS256).compact();

    }
    public String extractUsername(String token) {

      return Jwts.parserBuilder()
              .setSigningKey(getsigningKey())
              .build()
              .parseClaimsJws(token)
              .getBody()
              .getSubject();

    }
   public Key getsigningKey(){
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

   public boolean validateToken(String token){
        try
        {
        Jwts.parserBuilder()
                .setSigningKey(getsigningKey())
                .build()
                .parseClaimsJws(token);
        return true;

   } catch (Exception e) {
            return false;
        }
        }
}
