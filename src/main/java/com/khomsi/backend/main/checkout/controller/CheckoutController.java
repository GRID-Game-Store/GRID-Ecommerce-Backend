package com.khomsi.backend.main.checkout.controller;

import com.khomsi.backend.main.checkout.apis.Payment;
import com.khomsi.backend.main.checkout.model.dto.CheckoutItemDto;
import com.khomsi.backend.main.checkout.model.dto.stripe.StripeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.khomsi.backend.сonfig.ApplicationConfig.BEARER_KEY_SECURITY_SCHEME;

@RestController
@Tag(name = "Checkout", description = "CRUD operation for Checkout Controller")
@RequestMapping("/api/v1/checkout")
@Validated
@RequiredArgsConstructor
public class CheckoutController {
    private final Payment payment;

    @PostMapping("/stripe/create-payment")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Stripe create payment")
    public ResponseEntity<StripeResponse> checkoutList(@RequestBody List<CheckoutItemDto> checkoutItemDtoList
            , HttpServletRequest request) {
        // create the stripe session
        StripeResponse stripeResponse = payment.createPayment(checkoutItemDtoList, request);
        // send the stripe session id in response
        return ResponseEntity
                .status(stripeResponse.httpStatus())
                .body(stripeResponse);
    }

    // Check and place the order if success
    @PostMapping("/stripe/capture-payment")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Payment capture to place the order")
    public ResponseEntity<StripeResponse> placeOrder(@RequestParam("sessionId") String sessionId) {
        StripeResponse stripeResponse = payment.capturePayment(sessionId);
        return ResponseEntity
                .status(stripeResponse.httpStatus())
                .body(stripeResponse);
    }
}
