package com.khomsi.backend.main.checkout.apis;

import com.khomsi.backend.main.checkout.model.dto.stripe.PaymentResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface StripeService {
    PaymentResponse createPayment(HttpServletRequest url);

    PaymentResponse capturePayment(String sessionId);
}