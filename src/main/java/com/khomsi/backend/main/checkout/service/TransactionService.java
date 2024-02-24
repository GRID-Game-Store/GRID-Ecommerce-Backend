package com.khomsi.backend.main.checkout.service;

import com.khomsi.backend.additional.cart.model.dto.CartItemDto;
import com.khomsi.backend.main.checkout.model.dto.TransactionDTO;
import com.khomsi.backend.main.checkout.model.enums.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    void completeTransaction(String sessionId);

    void placeTemporaryTransaction(String sessionId, String url, boolean withBalance,
                                   PaymentMethod paymentMethod);

    BigDecimal getTotalAmountForBill(boolean withBalance, List<CartItemDto> cartItemDtoList);

    List<TransactionDTO> transactionList();
}
