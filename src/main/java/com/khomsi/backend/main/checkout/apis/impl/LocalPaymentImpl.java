package com.khomsi.backend.main.checkout.apis.impl;

import com.khomsi.backend.additional.cart.model.dto.CartDTO;
import com.khomsi.backend.additional.cart.model.dto.CartItemDto;
import com.khomsi.backend.additional.cart.service.CartService;
import com.khomsi.backend.main.checkout.model.dto.stripe.CreatePaymentResponse;
import com.khomsi.backend.main.checkout.model.dto.stripe.PaymentResponse;
import com.khomsi.backend.main.checkout.model.entity.Transaction;
import com.khomsi.backend.main.checkout.model.enums.BalanceAction;
import com.khomsi.backend.main.checkout.model.enums.PaymentMethod;
import com.khomsi.backend.main.checkout.service.TransactionService;
import com.khomsi.backend.main.user.model.entity.UserInfo;
import com.khomsi.backend.main.user.repository.UserInfoRepository;
import com.khomsi.backend.main.user.service.UserInfoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.khomsi.backend.main.checkout.apis.impl.ApiResponseBuilder.buildFailureResponse;
import static com.khomsi.backend.main.checkout.apis.impl.ApiResponseBuilder.buildResponse;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class LocalPaymentImpl implements LocalPaymentService {
    private final CartService cartService;
    private final UserInfoService userInfoService;
    private final UserInfoRepository userInfoRepository;
    private final TransactionService transactionService;

    @Override
    public PaymentResponse createPayment() {
        UserInfo existingUser = userInfoService.getUserInfo();
        CartDTO cartDto = cartService.cartItems();
        List<CartItemDto> cartItemDtoList = cartDto.cartItems();
        if (cartItemDtoList.isEmpty()
                || cartDto.totalCost().compareTo(existingUser.getBalance()) > 0) {
            return buildFailureResponse("Insufficient funds or empty cart", HttpStatus.BAD_REQUEST);
        }
        BigDecimal newBalance = existingUser.getBalance().subtract(cartDto.totalCost());
        existingUser.setBalance(newBalance);
        userInfoRepository.save(existingUser);
        String transactionId = UUID.randomUUID().toString();
        transactionService.placeTemporaryTransaction(null, transactionId, null,
                BalanceAction.NO_ACTION, PaymentMethod.LOCAL);
        return buildResponse(CreatePaymentResponse.builder()
                .sessionId(transactionId).build(), "Local payment is successfully created!");
    }

    @Override
    public PaymentResponse capturePayment(String sessionId) {
        UserInfo userInfo = userInfoService.getUserInfo();
        Optional<Transaction> optionalTransaction = transactionService.getTransaction(sessionId, userInfo);
        if (optionalTransaction.isEmpty()) {
            return buildFailureResponse("Local payment captured failed for session ID: " + sessionId,
                    HttpStatus.BAD_REQUEST);
        }
        transactionService.completeTransaction(sessionId);
        return buildResponse(null,
                "Local payment successfully captured for session ID: " + sessionId);
    }
}
