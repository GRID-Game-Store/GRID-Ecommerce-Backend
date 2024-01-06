package com.khomsi.grid.main.security.jwt;

import lombok.Builder;

@Builder
public record JwtResponse(String token) {
}