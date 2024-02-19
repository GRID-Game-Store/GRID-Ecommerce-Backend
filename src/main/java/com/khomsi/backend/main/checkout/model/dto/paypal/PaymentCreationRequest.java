package com.khomsi.backend.main.checkout.model.dto.paypal;

import lombok.Builder;

import java.util.List;
@Builder
public record PaymentCreationRequest(String intent, List<PurchaseUnit> purchase_units, PaymentSource payment_source) {
}