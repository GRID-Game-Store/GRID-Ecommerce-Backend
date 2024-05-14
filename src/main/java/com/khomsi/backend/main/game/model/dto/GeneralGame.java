package com.khomsi.backend.main.game.model.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;


@Builder
public record GeneralGame(List<ShortGameModel> games, long totalItems, int totalPages, int currentPage,
                          BigDecimal maxPrice) {
}

