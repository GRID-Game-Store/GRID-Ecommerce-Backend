package com.khomsi.backend.main.user.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.math.BigDecimal;
@Builder
public record FullUserInfoDTO(
        @NotBlank
        String externalId,
        @NotBlank
        String email,
        String birthdate,
        @NotBlank
        String givenName,
        @NotBlank
        String familyName,
        @NotBlank
        String gender,
        BigDecimal balance
) {
}
