package com.khomsi.backend.main.checkout.service;

import com.khomsi.backend.additional.cart.model.dto.CartItemDto;
import com.khomsi.backend.main.checkout.model.dto.TransactionDTO;
import com.khomsi.backend.main.checkout.model.enums.BalanceAction;
import com.khomsi.backend.main.checkout.model.enums.PaymentMethod;
import com.khomsi.backend.main.checkout.model.response.TransactionResponse;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    void completeTransaction(String sessionId);

    void placeTemporaryTransaction(BigDecimal amount, String sessionId, String url, BalanceAction withBalance,
                                   PaymentMethod paymentMethod);

    @Transactional
    TransactionResponse returnTransactionToCart(String sessionId);

    BigDecimal getTotalAmountForBill(BalanceAction balanceAction, List<CartItemDto> cartItemDtoList);

    List<TransactionDTO> transactionList();

    BigDecimal calculateTotalAmount(BigDecimal amount, BalanceAction balanceAction, List<CartItemDto> cartItemDtoList);
}
