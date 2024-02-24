package com.khomsi.backend.main.checkout.controller;

import com.khomsi.backend.main.checkout.apis.PaypalService;
import com.khomsi.backend.main.checkout.apis.StripeService;
import com.khomsi.backend.main.checkout.apis.impl.LocalPaymentService;
import com.khomsi.backend.main.checkout.model.dto.stripe.PaymentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.khomsi.backend.сonfig.ApplicationConfig.BEARER_KEY_SECURITY_SCHEME;

@RestController
@Tag(name = "Checkout", description = "CRUD operation for Checkout Controller")
@RequestMapping("/api/v1/checkout")
@Validated
@RequiredArgsConstructor
public class CheckoutController {
    private final StripeService stripeService;
    private final PaypalService paypalService;
    private final LocalPaymentService localPaymentService;

    @PostMapping("/grid/pay")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Local endpoint to create payment")
    public ResponseEntity<PaymentResponse> checkoutLocal() {
        PaymentResponse paymentResponse = localPaymentService.createPayment();
        return ResponseEntity
                .status(paymentResponse.httpStatus())
                .body(paymentResponse);
    }

    @PostMapping("/stripe/create-payment")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Stripe endpoint to create session")
    public ResponseEntity<PaymentResponse> checkoutStripe(boolean withBalance, HttpServletRequest request) {
        // create the stripe session
        PaymentResponse paymentResponse = stripeService.createPayment(withBalance, request);
        // send the stripe session id in response
        return ResponseEntity
                .status(paymentResponse.httpStatus())
                .body(paymentResponse);
    }

    // Check and place the order if success
    @PostMapping("/stripe/capture-payment")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Stripe capture to place the order")
    public ResponseEntity<PaymentResponse> placeStripeOrder(@RequestParam("sessionId") String sessionId) {
        PaymentResponse paymentResponse = stripeService.capturePayment(sessionId);
        return ResponseEntity
                .status(paymentResponse.httpStatus())
                .body(paymentResponse);
    }

    @PostMapping("/paypal/create-payment")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Paypal endpoint to create session")
    public ResponseEntity<PaymentResponse> checkoutPayPal(boolean withBalance, HttpServletRequest request) {
        // create the stripe session
        PaymentResponse paymentResponse = paypalService.createPayment(withBalance, request);
        // send the stripe session id in response
        return ResponseEntity
                .status(paymentResponse.httpStatus())
                .body(paymentResponse);
    }

    @PostMapping("/paypal/capture-payment")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "PaypalService capture to place the order")
    public ResponseEntity<PaymentResponse> placePayPalOrder(@RequestParam("token") String token) {
        PaymentResponse paymentResponse = paypalService.capturePayment(token);
        return ResponseEntity
                .status(paymentResponse.httpStatus())
                .body(paymentResponse);
    }
}
