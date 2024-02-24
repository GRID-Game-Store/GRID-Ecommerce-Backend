package com.khomsi.backend.main.checkout.apis.impl;

import com.khomsi.backend.main.checkout.model.dto.stripe.PaymentResponse;
import com.khomsi.backend.main.checkout.model.enums.Constant;
import org.springframework.http.HttpStatus;

public class ApiResponseBuilder {

    public static <T> PaymentResponse buildResponse(T responseData, String message) {
        return PaymentResponse.builder()
                .status(Constant.SUCCESS.name())
                .message(message)
                .httpStatus(HttpStatus.OK.value())
                .data(responseData)
                .build();
    }

    public static <T> PaymentResponse buildFailureResponse(String message, HttpStatus httpStatus, T responseData) {
        return PaymentResponse.builder()
                .status(Constant.FAILURE.name())
                .message(message)
                .httpStatus(httpStatus.value())
                .data(responseData)
                .build();
    }

    public static PaymentResponse buildFailureResponse(String message, HttpStatus httpStatus) {
        return buildFailureResponse(message, httpStatus, null);
    }
}