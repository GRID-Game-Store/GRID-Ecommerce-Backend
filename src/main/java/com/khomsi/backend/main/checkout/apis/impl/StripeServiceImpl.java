package com.khomsi.backend.main.checkout.apis.impl;

import com.khomsi.backend.additional.cart.model.dto.CartDTO;
import com.khomsi.backend.additional.cart.model.dto.CartItemDto;
import com.khomsi.backend.additional.cart.service.CartService;
import com.khomsi.backend.main.checkout.apis.StripeService;
import com.khomsi.backend.main.checkout.model.dto.stripe.CapturePaymentResponse;
import com.khomsi.backend.main.checkout.model.dto.stripe.CreatePaymentResponse;
import com.khomsi.backend.main.checkout.model.dto.stripe.PaymentResponse;
import com.khomsi.backend.main.checkout.model.enums.Constant;
import com.khomsi.backend.main.checkout.model.enums.PaymentMethod;
import com.khomsi.backend.main.checkout.service.TransactionService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.khomsi.backend.main.checkout.model.enums.PaymentEndpoints.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class StripeServiceImpl implements StripeService {
    @Value("${app.payment.stripe.stripe-secret}")
    private String secretKey;
    @Value("${app.payment.stripe.currency}")
    private String currency;
    @Value("${app.payment.unitAmount}")
    private String unitAmount;
    private final TransactionService transactionService;
    private final CartService cartService;

    public PaymentResponse createPayment(HttpServletRequest url) {
        CartDTO cartDto = cartService.cartItems();
        List<CartItemDto> cartItemDtoList = cartDto.cartItems();
        if (cartItemDtoList.isEmpty()) {
            return buildFailureResponse("Cart is empty", HttpStatus.BAD_REQUEST);
        }
        try {
            Stripe.apiKey = secretKey;

            List<SessionCreateParams.LineItem> sessionItemsList = createSessionItemsList(cartItemDtoList);

            SessionCreateParams params = buildSessionParams(url, sessionItemsList);

            Session session = Session.create(params);

            CreatePaymentResponse responseData = buildPaymentResponse(session);

            return buildResponse(responseData, "StripeService session created successfully");
        } catch (StripeException e) {
            log.error("Error creating payment session: {}", e.getMessage());
            return buildFailureResponse("StripeService session creation failed", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public PaymentResponse capturePayment(String sessionId) {
        Stripe.apiKey = secretKey;
        try {
            Session session = Session.retrieve(sessionId);
            String status = session.getStatus();
            CapturePaymentResponse responseData = buildCapturePaymentResponse(sessionId, status);

            if (status.equalsIgnoreCase(Constant.STRIPE_SESSION_STATUS_SUCCESS.label)) {
                transactionService.placeOrder(sessionId, PaymentMethod.STRIPE);
                return buildResponse(responseData,
                        "StripeService successfully captured for session ID: " + sessionId);
            } else {
                return buildFailureResponse("StripeService capture failed for session ID: " + sessionId,
                        HttpStatus.BAD_REQUEST, responseData);
            }
        } catch (StripeException e) {
            log.error("Error capturing payment: {}", e.getMessage());
            return buildFailureResponse("StripeService capture failed due to a server error.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private CapturePaymentResponse buildCapturePaymentResponse(String sessionId, String status) {
        return CapturePaymentResponse.builder()
                .sessionId(sessionId)
                .sessionStatus(status)
                .paymentStatus(status)
                .build();
    }

    private <T> PaymentResponse buildResponse(T responseData, String message) {
        return PaymentResponse.builder()
                .status(Constant.SUCCESS.name())
                .message(message)
                .httpStatus(HttpStatus.OK.value())
                .data(responseData)
                .build();
    }

    private <T> PaymentResponse buildFailureResponse(String message, HttpStatus httpStatus, T responseData) {
        return PaymentResponse.builder()
                .status(Constant.FAILURE.name())
                .message(message)
                .httpStatus(httpStatus.value())
                .data(responseData)
                .build();
    }

    private PaymentResponse buildFailureResponse(String message, HttpStatus httpStatus) {
        return buildFailureResponse(message, httpStatus, null);
    }

    // Build each product in the stripe checkout page
    private SessionCreateParams buildSessionParams(HttpServletRequest url,
                                                   List<SessionCreateParams.LineItem> sessionItemsList) {
        return SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setCancelUrl(createUrl(url, STRIPE_FAILED))
                .addAllLineItem(sessionItemsList)
                .setSuccessUrl(createUrl(url, STRIPE_SUCCESS))
                .build();
    }

    // Create total price
    SessionCreateParams.LineItem.PriceData createPriceData(CartItemDto checkoutItemDto) {
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency(currency)
                .setUnitAmount(checkoutItemDto.game().price().multiply(new BigDecimal(unitAmount)).longValueExact())
                .setProductData(
                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                .setName(checkoutItemDto.game().title())
                                .build())
                .build();
    }

    SessionCreateParams.LineItem createSessionLineItem(CartItemDto checkoutItemDto) {
        return SessionCreateParams.LineItem.builder()
                // set price for each product
                .setPriceData(createPriceData(checkoutItemDto))
                .setQuantity(1L)
                .build();
    }

    private List<SessionCreateParams.LineItem> createSessionItemsList(List<CartItemDto> cartItemDtoList) {
        return cartItemDtoList.stream().map(this::createSessionLineItem).toList();
    }

    private CreatePaymentResponse buildPaymentResponse(Session session) {
        return CreatePaymentResponse.builder()
                .sessionId(session.getId())
                .sessionUrl(session.getUrl())
                .build();
    }
}
