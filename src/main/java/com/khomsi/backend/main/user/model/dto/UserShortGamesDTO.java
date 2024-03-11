package com.khomsi.backend.main.user.model.dto;

import com.khomsi.backend.main.game.model.dto.ShortGameModel;
import lombok.Builder;

import java.time.Instant;
import java.time.LocalTime;

@Builder
public record UserShortGamesDTO(
        ShortGameModel game,
        Instant purchaseDate,
        LocalTime playtime
) {
}
