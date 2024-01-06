package com.khomsi.grid.main.authentication.model.reponse;

import lombok.Builder;

import java.util.List;

@Builder
public record AuthenticationLoginResponse(
        Long id,
        String username,
        String email,
        List<String> roles
) {
}
