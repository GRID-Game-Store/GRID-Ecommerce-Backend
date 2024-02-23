package com.khomsi.backend.main.checkout.mapper.impl;

import com.khomsi.backend.main.checkout.mapper.TransactionGamesMapper;
import com.khomsi.backend.main.checkout.mapper.TransactionMapper;
import com.khomsi.backend.main.checkout.model.dto.TransactionDTO;
import com.khomsi.backend.main.checkout.model.dto.TransactionGamesDTO;
import com.khomsi.backend.main.checkout.model.entity.Transaction;
import com.khomsi.backend.main.checkout.model.entity.TransactionGames;
import com.khomsi.backend.main.game.mapper.GameMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class TransactionGamesMapperImpl implements TransactionGamesMapper {
    private final GameMapper gameMapper;

    @Override
    public TransactionGamesDTO transactionGamesToTransactionGamesDTO(TransactionGames transactionGames) {
        return TransactionGamesDTO.builder()
                .id(transactionGames.getId())
                .games(gameMapper.toShortGame(transactionGames.getGames()))
                .priceOnPay(transactionGames.getPriceOnPay())
                .build();
    }
}
