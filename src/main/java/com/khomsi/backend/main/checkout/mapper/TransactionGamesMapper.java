package com.khomsi.backend.main.checkout.mapper;

import com.khomsi.backend.main.checkout.model.dto.TransactionGamesDTO;
import com.khomsi.backend.main.checkout.model.entity.TransactionGames;

public interface TransactionGamesMapper {
    TransactionGamesDTO transactionGamesToTransactionGamesDTO(TransactionGames transactionGames);
}