package com.khomsi.backend.main.checkout.model.dto.paypal;

public record AccessTokenResponse(String scope, String access_token,
                                  String token_type, String app_id, int expires_in, String nonce) {
}