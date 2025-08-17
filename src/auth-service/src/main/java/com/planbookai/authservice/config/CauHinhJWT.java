package com.planbookai.authservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "security.jwt")
@Getter
@Setter
public class CauHinhJWT {
    private String secret;
    private Long accessTokenExpirationSeconds; // e.g. 3600
    private Long refreshTokenExpirationSeconds; // e.g. 604800
    private String issuer;
}
