package com.khomsi.backend.main.admin.model.dto;

import com.khomsi.backend.main.game.model.entity.PermitAge;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public record GameDTO(
        @NotEmpty
        String title,
        String description,
        LocalDate releaseDate,
        String systemRequirements,
        @NotNull
        boolean active,
        String aboutGame,
        @NotNull
        BigDecimal price,
        @NotNull
        BigDecimal discount,
        @NotNull
        PermitAge permitAge,
        String coverImageUrl,
        @NotEmpty
        String bannerImageUrl,
        @NotEmpty
        String trailerUrl,
        String screenshotUrl,
        String trailerScreenshotUrl,
        @NotNull
        @Min(1)
        Long developer,
        @NotNull
        @Min(1)
        Long publisher,
        @NotNull
        Set<Long> tags,
        @NotNull
        Set<Long> genres,
        @NotNull
        Set<Long> platforms) {
}