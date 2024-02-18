package com.khomsi.backend.main.checkout.apis;

import com.khomsi.backend.main.checkout.model.dto.CheckoutItemDto;
import com.khomsi.backend.main.checkout.model.dto.stripe.StripeResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface Payment {
    StripeResponse createPayment(List<CheckoutItemDto> checkoutItemDtoList, HttpServletRequest url);

    StripeResponse capturePayment(String sessionId);
}