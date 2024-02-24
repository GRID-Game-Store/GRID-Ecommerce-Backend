package com.khomsi.backend.main.checkout.apis.impl;

import com.khomsi.backend.main.checkout.model.dto.stripe.PaymentResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface LocalPaymentService {
    PaymentResponse createPayment();
}