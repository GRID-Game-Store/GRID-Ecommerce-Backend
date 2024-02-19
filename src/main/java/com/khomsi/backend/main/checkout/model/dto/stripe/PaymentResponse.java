package com.khomsi.backend.main.checkout.model.dto.stripe;

import lombok.Builder;

@Builder
public record PaymentResponse<T>(String status, String message, Integer httpStatus, T data) {
}