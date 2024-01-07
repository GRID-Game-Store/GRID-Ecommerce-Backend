package com.khomsi.grid.main.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(
        String jwtCookieName,
        String jwtCookiePath,
        String jwtSecret,
        int jwtExpirationMs,
        int jwtCookieMaxAge
) {
}