package com.khomsi.grid.main.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(
        String jwtCookieName,
        String jwtRefreshCookieName,
        String jwtSecret,
        int jwtExpirationMs,
        int jwtRefreshExpirationMs,
        int jwtCookieMaxAge
) {
}