package com.planbookai.filter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.List;

@Component
@Slf4j
public class JwtAuthenticationFilter implements GatewayFilter {

    @Value("${jwt.secret:PlanBookAI-ReplaceThisSecretWithAProperStrongSecretKey!}")
    private String secret;

    // Danh sách public path, không cần JWT
    private final List<String> PUBLIC_PATHS = List.of(
            "/api/xac-thuc",
            "/api/quen-mat-khau",
            "/actuator"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        log.info("Incoming request path: {}", path);

        // ✅ Bypass endpoint public
        for (String publicPath : PUBLIC_PATHS) {
            if (path.startsWith(publicPath)) {
                log.info("Bypassing JWT check for public path: {}", path);
                return chain.filter(exchange);
            }
        }

        // ✅ Kiểm tra Authorization header
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("Missing or invalid Authorization header for path: {}", path);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);
        try {
            Key key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);

            log.info("JWT validated successfully for path: {}", path);
            return chain.filter(exchange);

        } catch (Exception e) {
            log.error("JWT validation error: {}", e.getMessage());
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }
}
