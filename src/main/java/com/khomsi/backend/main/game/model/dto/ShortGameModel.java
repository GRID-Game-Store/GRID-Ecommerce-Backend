package com.khomsi.backend.main.game.model.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ShortGameModel(
        Long id,
        String title,
        String description,
        BigDecimal price,
        BigDecimal discount,
        String coverImageUrl,
        boolean ownedByCurrentUser
) {
}
