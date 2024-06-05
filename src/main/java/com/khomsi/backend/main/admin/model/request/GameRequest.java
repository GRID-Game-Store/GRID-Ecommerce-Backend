package com.khomsi.backend.main.admin.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.khomsi.backend.additional.genre.model.entity.Genre;
import com.khomsi.backend.additional.platform.model.entity.Platform;
import com.khomsi.backend.additional.tag.model.entity.Tag;
import com.khomsi.backend.main.game.model.entity.PermitAge;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public record GameRequest(
        @NotEmpty
        @NotNull
        String title,
        String description,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
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
        Set<Tag> tags,
        @NotNull
        Set<Genre> genres,
        @NotNull
        Set<Platform> platforms) {
}