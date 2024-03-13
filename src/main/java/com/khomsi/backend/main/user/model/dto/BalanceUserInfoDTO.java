package com.khomsi.backend.main.user.model.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record BalanceUserInfoDTO(BigDecimal balance) {
}
