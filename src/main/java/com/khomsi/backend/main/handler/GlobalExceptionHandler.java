package com.khomsi.backend.main.handler;

import com.khomsi.backend.main.handler.dto.ErrorMessageResponse;
import com.khomsi.backend.main.handler.exception.GlobalServiceException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(GlobalServiceException.class)
    public ResponseEntity<?> handleResponseStatusException(
            GlobalServiceException e
    ) {
        return createDefaultErrorResponse(e);
    }

    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<?> handlePropertyReferenceException(PropertyReferenceException e) {
        return createExceptionResponse(e, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<?> createExceptionResponse(RuntimeException e, HttpStatus httpStatus) {
        String errorMessage = e.getMessage();
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("timestamp", LocalDateTime.now());
        responseBody.put("status", httpStatus);
        responseBody.put("message", errorMessage);
        return new ResponseEntity<>(responseBody, httpStatus);
    }

    private ResponseEntity<?> createDefaultErrorResponse(ResponseStatusException e) {
        return ResponseEntity.status(HttpStatus.valueOf(e.getStatusCode().value()))
                .body(getErrorMessageBody(e));
    }

    private ErrorMessageResponse getErrorMessageBody(ResponseStatusException e) {
        HttpStatus status = HttpStatus.valueOf(e.getStatusCode().value());
        return ErrorMessageResponse.builder()
                .message(e.getReason())
                .createdAt(Instant.now())
                .statusCode(status)
                .status(status.value())
                .type(MessageType.ERROR)
                .build();
    }

}