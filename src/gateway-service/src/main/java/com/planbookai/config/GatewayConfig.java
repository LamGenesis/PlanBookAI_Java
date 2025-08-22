package com.planbookai.config;

import com.planbookai.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()

            // Auth Service (không cần filter cho login, refresh, forgot password)
            .route("auth-service", r -> r.path("/api/xac-thuc/**", "/api/quen-mat-khau/**")
                .uri("lb://AUTH-SERVICE"))

            // User Service (cần filter JWT)
            .route("user-service", r -> r.path("/api/nguoi-dung/**", "/api/thong-tin-ca-nhan/**")
                .filters(f -> f.filter(jwtAuthenticationFilter))
                .uri("lb://USER-SERVICE"))

            .build();
    }
}
