package com.khomsi.backend.main.checkout.model.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record TransactionDTO(String transactionId, LocalDateTime createdAt, LocalDateTime updatedAt,
                             BigDecimal totalAmount, String paymentMethods,
                             Boolean paid, List<TransactionGamesDTO> transactionGames, Boolean withBalance,
                             String redirectUrl) {
}