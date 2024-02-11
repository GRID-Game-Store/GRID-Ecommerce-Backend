package com.khomsi.backend.additional.cart.model.dto;

import com.khomsi.backend.main.game.model.dto.ShortGameModel;
import com.khomsi.backend.main.game.model.entity.Game;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CartItemDto(Long cartId, @NotNull String userId, @NotNull @Min(1) Integer quantity, @NotNull ShortGameModel game) {
}