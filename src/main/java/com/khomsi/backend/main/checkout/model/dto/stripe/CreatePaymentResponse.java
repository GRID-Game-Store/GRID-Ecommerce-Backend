package com.khomsi.backend.main.checkout.model.dto.stripe;

import lombok.Builder;

@Builder
public record CreatePaymentResponse(String sessionId, String sessionUrl) {
}