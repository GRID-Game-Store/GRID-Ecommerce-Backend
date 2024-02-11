package com.khomsi.backend.additional.cart.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AddToCartDto(Long gameId, @NotNull @Min(1) Integer quantity) {
}