package com.khomsi.backend.main.checkout.model.dto.paypal;

import java.util.Date;
import java.util.List;

public record PaypalPaymentCaptureResponse(String id, String status, String intent, PaymentSourceCapture payment_source,
                                           List<PurchaseUnit> purchase_units, Payer payer, Date create_time, List<Link> links) {
}

record PaymentSourceCapture(PayPalCapture paypal) {
}

record PayPalCapture(Name name, String email_address, String account_id) {
}

record Name(String given_name, String surname) {
}

record Payer(Name name, String email_address, String payer_id) {
}