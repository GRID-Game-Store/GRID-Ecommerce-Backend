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
import java.util.stream.Collectors;

import static com.khomsi.backend.main.checkout.apis.impl.ApiResponseBuilder.buildFailureResponse;
import static com.khomsi.backend.main.checkout.apis.impl.ApiResponseBuilder.buildResponse;
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
    private BigDecimal unitAmount;
    private final TransactionService transactionService;
    private final CartService cartService;

    public PaymentResponse createPayment(boolean withBalance, HttpServletRequest url) {
        CartDTO cartDto = cartService.cartItems();
        List<CartItemDto> cartItemDtoList = cartDto.cartItems();
        if (cartItemDtoList.isEmpty()) {
            return buildFailureResponse("Cart is empty", HttpStatus.BAD_REQUEST);
        }
        try {
            Stripe.apiKey = secretKey;
            // Calculate total amount
            BigDecimal totalAmount = transactionService.getTotalAmountForBill(withBalance, cartItemDtoList);

            // Create session line item with total amount
            SessionCreateParams.LineItem sessionLineItem = createSessionLineItemWithTotal(totalAmount, cartItemDtoList);

            SessionCreateParams params = buildSessionParams(url, sessionLineItem);

            Session session = Session.create(params);

            CreatePaymentResponse responseData = buildPaymentResponse(session);
            transactionService.placeTemporaryTransaction(responseData.sessionId(), responseData.sessionUrl(),
                    withBalance, PaymentMethod.STRIPE);
            return buildResponse(responseData, "Stripe session created successfully");
        } catch (StripeException e) {
            log.error("Error creating payment session: {}", e.getMessage());
            return buildFailureResponse("Stripe session creation failed", HttpStatus.BAD_REQUEST);
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
                transactionService.completeTransaction(sessionId);
                return buildResponse(responseData,
                        "Stripe successfully captured for session ID: " + sessionId);
            } else {
                return buildFailureResponse("Stripe capture failed for session ID: " + sessionId,
                        HttpStatus.BAD_REQUEST, responseData);
            }
        } catch (StripeException e) {
            log.error("Error capturing payment: {}", e.getMessage());
            return buildFailureResponse("Stripe capture failed due to a server error.",
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

    // Build session parameters
    private SessionCreateParams buildSessionParams(HttpServletRequest url,
                                                   SessionCreateParams.LineItem sessionLineItem) {
        return SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setCancelUrl(createUrl(url, STRIPE_FAILED))
                .addLineItem(sessionLineItem)
                .setSuccessUrl(createUrl(url, STRIPE_SUCCESS))
                .build();
    }

    // Create session line item with total amount
    private SessionCreateParams.LineItem createSessionLineItemWithTotal(BigDecimal totalAmount,
                                                                        List<CartItemDto> cartItemDtoList) {
        return SessionCreateParams.LineItem.builder()
                .setPriceData(createPriceData(totalAmount, cartItemDtoList))
                .setQuantity(1L)
                .build();
    }

    // Create total price
    private SessionCreateParams.LineItem.PriceData createPriceData(BigDecimal totalAmount,
                                                                   List<CartItemDto> cartItemDtoList) {
        String description = cartItemDtoList.stream()
                .map(cartItem -> cartItem.game().title())
                .collect(Collectors.joining(", "));
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency(currency)
                .setUnitAmount(totalAmount.multiply(unitAmount).longValueExact())
                .setProductData(
                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                .setName("Total amount | GRID")
                                .setDescription(description)
                                .build())
                .build();
    }

    private CreatePaymentResponse buildPaymentResponse(Session session) {
        return CreatePaymentResponse.builder()
                .sessionId(session.getId())
                .sessionUrl(session.getUrl())
                .build();
    }
}
