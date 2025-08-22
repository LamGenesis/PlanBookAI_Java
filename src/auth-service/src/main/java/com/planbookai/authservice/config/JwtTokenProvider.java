package com.planbookai.authservice.config;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration-ms}")
    private long jwtExpirationMs;

    private Key key;

    @PostConstruct
    public void init() {
        // 1) Nếu jwtSecret là Base64 hợp lệ -> decode
        byte[] keyBytes = tryDecodeBase64(jwtSecret);
        if (keyBytes == null) {
            // 2) Nếu KHÔNG phải Base64 -> dùng bytes thường (UTF-8)
            keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        }

        // 3) Kiểm tra độ dài tối thiểu 32 bytes (256 bit) theo RFC 7518
        if (keyBytes.length < 32) {
            throw new IllegalArgumentException(
                "JWT secret must be at least 256 bits (32 bytes). " +
                "Current length=" + keyBytes.length + " bytes. " +
                "Use a longer secret or a Base64-encoded 256-bit key."
            );
        }

        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    private byte[] tryDecodeBase64(String secret) {
        try {
            // Chỉ decode nếu secret có dạng Base64 hợp lệ
            // Tránh decode nhầm chuỗi thường
            if (isMaybeBase64(secret)) {
                return Decoders.BASE64.decode(secret);
            }
            return null;
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    private boolean isMaybeBase64(String s) {
        // Heuristic nhẹ: chỉ gồm Base64 charset và có độ dài bội số 4
        if (s == null || s.isEmpty() || (s.length() % 4) != 0) return false;
        return s.matches("^[A-Za-z0-9+/=]+$");
    }

    public String generateToken(String username) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + jwtExpirationMs);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameFromJwt(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // (Tuỳ chọn) helper để sinh key Base64 an toàn rồi in ra logs khi cần:
    public static String generateSecureBase64Key() {
        Key k = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        return Encoders.BASE64.encode(k.getEncoded());
    }
}
