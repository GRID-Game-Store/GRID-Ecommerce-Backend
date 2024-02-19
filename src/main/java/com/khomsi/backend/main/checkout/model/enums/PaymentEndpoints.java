package com.khomsi.backend.main.checkout.model.enums;

import jakarta.servlet.http.HttpServletRequest;

public enum PaymentEndpoints {
    STRIPE_SUCCESS("/stripe/success"),
    STRIPE_FAILED("/stripe/failed"),
    PAYPAL_SUCCESS("/paypal/success"),
    PAYPAL_CANCEL("/payment/cancel");
    private final String path;

    PaymentEndpoints(String path) {
        this.path = path;
    }

    public static String createUrl(HttpServletRequest baseUrl, PaymentEndpoints endpoint) {
        return baseUrl.getHeader("Origin") + endpoint.path;
    }
}