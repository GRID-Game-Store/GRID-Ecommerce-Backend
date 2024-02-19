package com.khomsi.backend.main.checkout.model.dto.paypal;

import java.util.List;

public record PaymentCreationResponse(String id, String status, PaymentSource payment_source, List<Link> links) {

}