package com.khomsi.backend.main.checkout.model.enums;

import jakarta.servlet.http.HttpServletRequest;

public enum PaymentEndpoints {
    PAYMENT_SUCCESS("/payment/success"),
    PAYMENT_FAILED("/payment/failed");
    private final String path;

    PaymentEndpoints(String path) {
        this.path = path;
    }

    public static String createUrl(HttpServletRequest baseUrl, PaymentEndpoints endpoint) {
        return baseUrl.getHeader("Origin") + endpoint.path;
    }
}