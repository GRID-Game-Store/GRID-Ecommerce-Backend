package com.khomsi.backend.main.checkout.model.dto.stripe;

import lombok.Builder;

@Builder
public record CapturePaymentResponse(String sessionId, String sessionStatus, String paymentStatus) {
}