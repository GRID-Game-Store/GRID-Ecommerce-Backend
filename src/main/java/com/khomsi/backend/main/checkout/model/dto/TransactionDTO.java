package com.khomsi.backend.main.checkout.model.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
@Builder
public record TransactionDTO(String transactionId, LocalDate createdAt,
                             BigDecimal totalAmount, String paymentMethods,
                             Boolean paid, List<TransactionGamesDTO> transactionGames) {
}