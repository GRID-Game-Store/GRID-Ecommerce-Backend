package com.khomsi.backend.main.checkout.model.enums;

public enum Constant {
    SUCCESS("SUCCESS"),
    FAILURE("FAILURE"),
    STRIPE_SESSION_STATUS_SUCCESS("complete");
    public final String label;

    Constant(String label) {
        this.label = label;
    }
}