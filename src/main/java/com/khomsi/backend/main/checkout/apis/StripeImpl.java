package com.khomsi.backend.main.checkout.apis;

import com.khomsi.backend.main.checkout.model.dto.CheckoutItemDto;
import com.khomsi.backend.main.checkout.model.dto.stripe.CapturePaymentResponse;
import com.khomsi.backend.main.checkout.model.dto.stripe.CreatePaymentResponse;
import com.khomsi.backend.main.checkout.model.dto.stripe.StripeResponse;
import com.khomsi.backend.main.checkout.model.enums.Constant;
import com.khomsi.backend.main.checkout.service.CheckoutService;
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
public class StripeImpl implements Payment {
    @Value("${app.payment.stripe-secret}")
    private String secretKey;
    @Value("${app.payment.currency}")
    private String currency;
    @Value("${app.payment.unitAmount}")
    private String unitAmount;
    private final CheckoutService checkoutService;

    public StripeResponse createPayment(List<CheckoutItemDto> checkoutItemDtoList, HttpServletRequest url) {
        try {
            Stripe.apiKey = secretKey;

            List<SessionCreateParams.LineItem> sessionItemsList = createSessionItemsList(checkoutItemDtoList);

            SessionCreateParams params = buildSessionParams(url, sessionItemsList);

            Session session = Session.create(params);

            CreatePaymentResponse responseData = buildPaymentResponse(session);

            return buildResponse(responseData, "Payment session created successfully");
        } catch (StripeException e) {
            log.error("Error creating payment session: {}", e.getMessage());
            return buildFailureResponse("Payment session creation failed", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public StripeResponse capturePayment(String sessionId) {
        Stripe.apiKey = secretKey;
        try {
            Session session = Session.retrieve(sessionId);
            String status = session.getStatus();
            CapturePaymentResponse responseData = buildCapturePaymentResponse(sessionId, status);

            if (status.equalsIgnoreCase(Constant.STRIPE_SESSION_STATUS_SUCCESS.label)) {
                checkoutService.placeOrder(sessionId);
                return buildResponse(responseData,
                        "Payment successfully captured for session ID: " + sessionId);
            } else {
                return buildFailureResponse("Payment capture failed for session ID: " + sessionId,
                        HttpStatus.BAD_REQUEST, responseData);
            }
        } catch (StripeException e) {
            log.error("Error capturing payment: {}", e.getMessage());
            return buildFailureResponse("Payment capture failed due to a server error.",
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

    private <T> StripeResponse buildResponse(T responseData, String message) {
        return StripeResponse.builder()
                .status(Constant.SUCCESS.name())
                .message(message)
                .httpStatus(HttpStatus.OK.value())
                .data(responseData)
                .build();
    }

    private <T> StripeResponse buildFailureResponse(String message, HttpStatus httpStatus, T responseData) {
        return StripeResponse.builder()
                .status(Constant.FAILURE.name())
                .message(message)
                .httpStatus(httpStatus.value())
                .data(responseData)
                .build();
    }

    private StripeResponse buildFailureResponse(String message, HttpStatus httpStatus) {
        return buildFailureResponse(message, httpStatus, null);
    }

    // Create total price
    SessionCreateParams.LineItem.PriceData createPriceData(CheckoutItemDto checkoutItemDto) {
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency(currency)
                .setUnitAmount(checkoutItemDto.price().multiply(new BigDecimal(unitAmount)).longValueExact())
                .setProductData(
                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                .setName(checkoutItemDto.productName())
                                .build())
                .build();
    }

    // Build each product in the stripe checkout page
    private SessionCreateParams buildSessionParams(HttpServletRequest url,
                                                   List<SessionCreateParams.LineItem> sessionItemsList) {
        return SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setCancelUrl(createUrl(url, PAYMENT_FAILED))
                .addAllLineItem(sessionItemsList)
                .setSuccessUrl(createUrl(url, PAYMENT_SUCCESS))
                .build();
    }

    SessionCreateParams.LineItem createSessionLineItem(CheckoutItemDto checkoutItemDto) {
        return SessionCreateParams.LineItem.builder()
                // set price for each product
                .setPriceData(createPriceData(checkoutItemDto))
                .setQuantity(1L)
                .build();
    }

    private List<SessionCreateParams.LineItem> createSessionItemsList(List<CheckoutItemDto> checkoutItemDtoList) {
        return checkoutItemDtoList.stream().map(this::createSessionLineItem).toList();
    }

    private CreatePaymentResponse buildPaymentResponse(Session session) {
        return CreatePaymentResponse.builder()
                .sessionId(session.getId())
                .sessionUrl(session.getUrl())
                .build();
    }
}
