package com.khomsi.grid.main.game.model.dto;

import com.khomsi.grid.additional.genre.model.entity.Genre;
import com.khomsi.grid.additional.platform.model.entity.Platform;
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
        Set<Platform> platforms
) {
}
