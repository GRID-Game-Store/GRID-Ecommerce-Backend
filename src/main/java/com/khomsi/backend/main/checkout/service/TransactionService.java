package com.khomsi.backend.main.checkout.service;

import com.khomsi.backend.main.checkout.model.dto.TransactionDTO;
import com.khomsi.backend.main.checkout.model.enums.PaymentMethod;

import java.util.List;

public interface TransactionService {
    void placeOrder(String sessionId, PaymentMethod paymentMethod);

    List<TransactionDTO> transactionList();
}
