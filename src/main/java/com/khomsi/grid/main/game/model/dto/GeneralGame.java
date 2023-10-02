package com.khomsi.grid.main.game.model.dto;

import com.khomsi.grid.main.game.model.entity.Game;
import lombok.Builder;

import java.util.List;


@Builder
public record GeneralGame(List<Game> games, long totalItems, int totalPages, int currentPage) {
}

