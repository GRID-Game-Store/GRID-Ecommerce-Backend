package com.khomsi.backend.main.user.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ShortUserInfoDTO(
        @NotBlank
        String externalId,
        @NotBlank
        String email,
        BigDecimal balance
) {
}
