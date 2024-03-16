package com.khomsi.backend.main.game.model.dto;

import com.khomsi.backend.additional.genre.model.entity.Genre;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.Set;

@Builder
public record PopularGameModel(
        Long id,
        String title,
        String description,
        BigDecimal price,
        String coverImageUrl,
        Set<Genre> genres,
        boolean ownedByCurrentUser
) {
}
