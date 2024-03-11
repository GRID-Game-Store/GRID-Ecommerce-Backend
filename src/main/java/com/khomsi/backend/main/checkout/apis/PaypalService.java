package com.khomsi.backend.main.checkout.apis;

import com.khomsi.backend.main.checkout.model.dto.stripe.PaymentResponse;
import com.khomsi.backend.main.checkout.model.enums.BalanceAction;
import com.khomsi.backend.main.checkout.model.enums.PaymentMethod;
import jakarta.servlet.http.HttpServletRequest;

import java.math.BigDecimal;

public interface PaypalService {
    PaymentResponse createBalanceRecharge(BigDecimal amount, HttpServletRequest url);

    PaymentResponse createPayment(BalanceAction withBalance, HttpServletRequest url);

    PaymentResponse capturePayment(String sessionId);
}