package com.khomsi.backend.additional.cart.model.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;
@Builder
public record CartDTO(List<CartItemDto> cartItems, BigDecimal totalCost) {
}