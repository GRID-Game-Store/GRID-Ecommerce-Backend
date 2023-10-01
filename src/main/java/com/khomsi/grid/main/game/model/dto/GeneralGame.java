package com.khomsi.grid.main.game.model.dto;

import com.khomsi.grid.main.game.model.entity.Game;
import lombok.Builder;
import org.springframework.data.domain.Page;

@Builder
public record GeneralGame(Page<Game> games) {
}
