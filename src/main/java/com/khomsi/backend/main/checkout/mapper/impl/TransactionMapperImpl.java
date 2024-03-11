package com.khomsi.backend.main.checkout.mapper.impl;

import com.khomsi.backend.main.checkout.mapper.TransactionGamesMapper;
import com.khomsi.backend.main.checkout.mapper.TransactionMapper;
import com.khomsi.backend.main.checkout.model.dto.TransactionDTO;
import com.khomsi.backend.main.checkout.model.entity.Transaction;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TransactionMapperImpl implements TransactionMapper {

    private final TransactionGamesMapper transactionGamesMapper;

    @Override
    public TransactionDTO transactionToTransactionDTO(Transaction transaction) {
        return TransactionDTO.builder()
                .transactionId(transaction.getTransactionId())
                .createdAt(transaction.getCreatedAt())
                .totalAmount(transaction.getTotalAmount())
                .paymentMethods(transaction.getPaymentMethods())
                .balanceAction(transaction.getBalanceAction())
                .updatedAt(transaction.getUpdatedAt())
                .redirectUrl(transaction.getRedirectUrl())
                .paid(transaction.getPaid())
                .transactionGames(transaction.getTransactionGames().stream()
                        .map(transactionGamesMapper::transactionGamesToTransactionGamesDTO)
                        .toList())
                .build();
    }
}
