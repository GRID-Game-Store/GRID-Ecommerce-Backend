package com.khomsi.backend.main.checkout.model.dto.paypal;

public record ExperienceContext(String payment_method_preference, String payment_method_selected, String brand_name,
                                String locale, String landing_page, String shipping_preference, String user_action,
                                String return_url, String cancel_url) {
}