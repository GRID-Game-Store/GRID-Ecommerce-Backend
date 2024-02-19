package com.khomsi.backend.main.checkout.service;

import com.khomsi.backend.main.checkout.model.enums.PaymentMethod;

public interface CheckoutService {
    void placeOrder(String sessionId, PaymentMethod paymentMethod);
}
