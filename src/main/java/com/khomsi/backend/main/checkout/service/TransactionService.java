package com.khomsi.backend.main.checkout.service;

import com.khomsi.backend.additional.cart.model.dto.CartItemDto;
import com.khomsi.backend.main.checkout.model.dto.TransactionDTO;
import com.khomsi.backend.main.checkout.model.enums.BalanceAction;
import com.khomsi.backend.main.checkout.model.enums.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    void completeTransaction(String sessionId);

    void placeTemporaryTransaction(BigDecimal amount, String sessionId, String url, BalanceAction withBalance,
                                   PaymentMethod paymentMethod);

    BigDecimal getTotalAmountForBill(BalanceAction balanceAction, List<CartItemDto> cartItemDtoList);

    List<TransactionDTO> transactionList();

    BigDecimal calculateTotalAmount(BigDecimal amount, BalanceAction balanceAction, List<CartItemDto> cartItemDtoList);
}
