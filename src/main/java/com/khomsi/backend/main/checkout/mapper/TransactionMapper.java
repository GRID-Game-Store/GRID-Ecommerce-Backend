package com.khomsi.backend.main.checkout.mapper;

import com.khomsi.backend.main.checkout.model.dto.TransactionDTO;
import com.khomsi.backend.main.checkout.model.entity.Transaction;

public interface TransactionMapper {
    TransactionDTO transactionToTransactionDTO(Transaction transaction);
}
