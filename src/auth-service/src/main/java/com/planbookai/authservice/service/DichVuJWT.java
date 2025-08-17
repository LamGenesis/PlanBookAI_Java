package com.planbookai.authservice.service;

import com.planbookai.authservice.config.CauHinhJWT;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;

@Service
public class DichVuJWT {
    private final CauHinhJWT cauHinh;
    private Key key;

    public DichVuJWT(CauHinhJWT cauHinh) {
        this.cauHinh = cauHinh;
    }

    @PostConstruct
    public void init() {
        String secret = cauHinh.getSecret();
        if (secret == null || secret.length() < 32) {
            // Warning default secret for dev only
            secret = "PlanBookAI-ReplaceThisSecretWithAProperStrongSecretKey!";
        }
        key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String taoAccessToken(String email) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + cauHinh.getAccessTokenExpirationSeconds() * 1000);
        return Jwts.builder()
                .setSubject(email)
                .setIssuer(cauHinh.getIssuer())
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String taoRefreshToken(String email) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + cauHinh.getRefreshTokenExpirationSeconds() * 1000);
        return Jwts.builder()
                .setSubject(email)
                .setIssuer(cauHinh.getIssuer())
                .setIssuedAt(now)
                .setExpiration(exp)
                .claim("typ", "refresh")
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public Long getAccessTokenExpirySeconds() {
        return cauHinh.getAccessTokenExpirationSeconds();
    }
}
