package com.khomsi.backend.main.checkout.apis;

import com.khomsi.backend.main.checkout.model.dto.stripe.PaymentResponse;
import com.khomsi.backend.main.checkout.model.enums.BalanceAction;
import com.khomsi.backend.main.checkout.model.enums.PaymentMethod;
import jakarta.servlet.http.HttpServletRequest;

import java.math.BigDecimal;

public interface StripeService {

    PaymentResponse createBalanceRecharge(BigDecimal amount, HttpServletRequest url);

    PaymentResponse createPayment(BalanceAction balanceAction, HttpServletRequest url);

    PaymentResponse capturePayment(String sessionId);
}