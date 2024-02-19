package com.khomsi.backend.main.checkout.apis.impl;

import com.khomsi.backend.additional.cart.model.dto.CartDTO;
import com.khomsi.backend.additional.cart.model.dto.CartItemDto;
import com.khomsi.backend.additional.cart.service.CartService;
import com.khomsi.backend.main.checkout.apis.CurrencyService;
import com.khomsi.backend.main.checkout.apis.PaypalService;
import com.khomsi.backend.main.checkout.model.dto.paypal.*;
import com.khomsi.backend.main.checkout.model.dto.stripe.PaymentResponse;
import com.khomsi.backend.main.checkout.model.enums.Constant;
import com.khomsi.backend.main.checkout.model.enums.PaymentMethod;
import com.khomsi.backend.main.checkout.service.CheckoutService;
import com.khomsi.backend.main.handler.exception.GlobalServiceException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.khomsi.backend.main.checkout.model.enums.PaymentEndpoints.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PayPalImpl implements PaypalService {
    @Value("${app.payment.paypal.paypal-id}")
    private String clientId;
    @Value("${app.payment.paypal.paypal-secret}")
    private String clientSecret;
    @Value("${app.payment.paypal.base-api-url}")
    private String baseApiUrl;
    @Value("${app.payment.paypal.currency_USD}")
    private String currencyUSD;
    private final RestTemplate restTemplate;
    private final CheckoutService checkoutService;
    private final CurrencyService currencyService;
    private final CartService cartService;

    @Override
    public PaymentResponse createPayment(HttpServletRequest url) {
        CartDTO cartDto = cartService.cartItems();
        List<CartItemDto> cartItemDtoList = cartDto.cartItems();
        if (cartItemDtoList.isEmpty()) {
            return buildResponse("Cart is empty", HttpStatus.BAD_REQUEST.value(), Constant.FAILURE.name());
        }
        PaymentCreationRequest paymentRequest = new PaymentCreationRequest(
                "CAPTURE",
                createPurchaseUnits(cartItemDtoList),
                createPaymentSource(url)
        );
        ResponseEntity<PaymentCreationResponse> paymentCreationResponse = restTemplate.exchange(
                baseApiUrl + "/v2/checkout/orders",
                HttpMethod.POST, getRequestBody(paymentRequest), PaymentCreationResponse.class);

        if (!paymentCreationResponse.getStatusCode().is2xxSuccessful())
            throw new GlobalServiceException(HttpStatus.BAD_REQUEST, "Can't create paypal payment!");

        return buildResponse(paymentCreationResponse.getBody().links().get(1).href());
    }

    @Override
    public PaymentResponse capturePayment(String token) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(generateAccessToken());
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<PaypalPaymentCaptureResponse> responseEntity = restTemplate.exchange(
                baseApiUrl + "/v2/checkout/orders/" + token + "/capture",
                HttpMethod.POST, httpEntity, PaypalPaymentCaptureResponse.class);

        if (!responseEntity.getStatusCode().is2xxSuccessful())
            throw new GlobalServiceException(HttpStatus.BAD_REQUEST, "Can't capture paypal payment!");

        if (responseEntity.getBody().status().equals("COMPLETED")) {
            checkoutService.placeOrder(token, PaymentMethod.PAYPAL);
            return buildResponse("PaypalService successfully captured for session ID: " + token);
        }
        return buildResponse("PaypalService capture failed for session ID: " + token,
                HttpStatus.BAD_REQUEST.value(), Constant.FAILURE.name());
    }

    private PaymentResponse buildResponse(String message) {
        return buildResponse(message, HttpStatus.OK.value(), Constant.SUCCESS.name());
    }

    private PaymentResponse buildResponse(String message, Integer httpStatus, String constant) {
        return PaymentResponse.builder()
                .status(constant)
                .message(message)
                .httpStatus(httpStatus)
                .data(null)
                .build();
    }

    public HttpEntity<?> getRequestBody(Object body) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(generateAccessToken());
        httpHeaders.set("PayPal-Request-Id", UUID.randomUUID().toString());
        log.info("body={}", body);
        log.info("httpHeaders={}", httpHeaders);
        return new HttpEntity<>(body, httpHeaders);
    }

    private String generateAccessToken() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.setBasicAuth(clientId, clientSecret);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "client_credentials");

        HttpEntity<?> httpEntity = new HttpEntity<>(requestBody, httpHeaders);

        ResponseEntity<AccessTokenResponse> accessTokenResponse = restTemplate.exchange(
                baseApiUrl + "/v1/oauth2/token",
                HttpMethod.POST, httpEntity, AccessTokenResponse.class);

        if (!accessTokenResponse.getStatusCode().is2xxSuccessful())
            throw new GlobalServiceException(HttpStatus.BAD_REQUEST, "Can't generate paypal access token!");
        log.info("access token={}", accessTokenResponse.getBody().access_token());
        return accessTokenResponse.getBody().access_token();
    }

    private List<PurchaseUnit> createPurchaseUnits(List<CartItemDto> checkoutItems) {
        List<PurchaseUnit> purchaseUnits = new ArrayList<>();
        checkoutItems.forEach(checkoutItemDto -> {
            BigDecimal convertedPrice = currencyService.convertToUSD(checkoutItemDto.game().price());
            String paymentId = UUID.randomUUID().toString();
            purchaseUnits.add(new PurchaseUnit(paymentId, new Amount(currencyUSD,
                    String.valueOf(convertedPrice))));
        });
        return purchaseUnits;
    }

    private PaymentSource createPaymentSource(HttpServletRequest url) {
        return new PaymentSource(
                new Paypal(
                        new ExperienceContext(
                                "IMMEDIATE_PAYMENT_REQUIRED",
                                "PAYPAL",
                                "GRID",
                                "en-UA",
                                "LOGIN",
                                "NO_SHIPPING",
                                "PAY_NOW",
                                createUrl(url, PAYPAL_SUCCESS),
                                createUrl(url, PAYPAL_CANCEL)
                        )
                )
        );
    }
}
