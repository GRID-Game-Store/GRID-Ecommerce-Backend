package com.khomsi.backend.main.checkout.model.dto;

import com.khomsi.backend.main.checkout.model.enums.BalanceAction;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record TransactionDTO(String transactionId, String userId, LocalDateTime createdAt, LocalDateTime updatedAt,
                             BigDecimal totalAmount, String paymentMethods,
                             Boolean paid, List<TransactionGamesDTO> transactionGames,
                             String redirectUrl, BalanceAction balanceAction) {
}