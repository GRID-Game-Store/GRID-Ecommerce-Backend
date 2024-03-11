package com.khomsi.backend.main.checkout.model.dto;

import lombok.Builder;

import java.math.BigDecimal;
@Builder
public record CheckoutItemDto(String productName, BigDecimal price, Long gameId) {
}