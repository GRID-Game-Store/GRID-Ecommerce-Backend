package com.khomsi.backend.main.handler;

import com.khomsi.backend.main.handler.dto.ErrorMessageResponse;
import com.khomsi.backend.main.handler.exception.GlobalServiceException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    //TODO change this handler to handel all exceptions in once
    @ExceptionHandler(GlobalServiceException.class)
    public ResponseEntity<?> handleResponseStatusException(
            GlobalServiceException e
    ) {
        return createDefaultErrorResponse(e);
    }

    @ExceptionHandler(InvalidBearerTokenException.class)
    public ResponseEntity<?> handleInvalidBearerTokenException(InvalidBearerTokenException e) {
        return createInvalidBearerTokenExceptionResponse(e);
    }

    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<?> handlePropertyReferenceException(PropertyReferenceException e) {
        return createPropertyReferenceExceptionResponse(e);
    }

    private ResponseEntity<?> createInvalidBearerTokenExceptionResponse(InvalidBearerTokenException e) {
        String errorMessage = "Invalid JWT token: " + e.getMessage();
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("timestamp", LocalDateTime.now());
        responseBody.put("status", HttpStatus.UNAUTHORIZED.value());
        responseBody.put("error", "Unauthorized");
        responseBody.put("message", errorMessage);
        return new ResponseEntity<>(responseBody, HttpStatus.UNAUTHORIZED);
    }

    private ResponseEntity<?> createPropertyReferenceExceptionResponse(PropertyReferenceException e) {
        String errorMessage = "No property '" + e.getPropertyName() + "' found.";
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("timestamp", LocalDateTime.now());
        responseBody.put("status", HttpStatus.BAD_REQUEST.value());
        responseBody.put("error", "Bad Request");
        responseBody.put("message", errorMessage);
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
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