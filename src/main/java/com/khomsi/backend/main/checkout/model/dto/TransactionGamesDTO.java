package com.khomsi.backend.main.checkout.model.dto;

import com.khomsi.backend.main.game.model.dto.ShortGameModel;
import com.khomsi.backend.main.game.model.entity.Game;
import lombok.Builder;

import java.math.BigDecimal;
@Builder
public record TransactionGamesDTO(Long id, ShortGameModel games, BigDecimal priceOnPay) {
}