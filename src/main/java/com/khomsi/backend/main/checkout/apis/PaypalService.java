package com.khomsi.backend.main.checkout.apis;

import com.khomsi.backend.main.checkout.model.dto.CheckoutItemDto;
import com.khomsi.backend.main.checkout.model.dto.stripe.PaymentResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface PaypalService {
    PaymentResponse createPayment(HttpServletRequest url);

    PaymentResponse capturePayment(String sessionId);
}