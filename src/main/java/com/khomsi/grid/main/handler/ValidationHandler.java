package com.khomsi.grid.main.handler;

import com.khomsi.grid.main.authentication.model.reponse.MessageResponse;
import com.khomsi.grid.main.security.exception.JwtAuthenticationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
@RequiredArgsConstructor
public class ValidationHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e) {
        HttpStatus status = BAD_REQUEST;
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();

        ErrorMessageResponse response = getErrorMessageResponse(e, status);
        List<?> additionalMessages = violations.stream()
                .map(exception -> ErrorMessage.builder()
                        .message(exception.getMessage())
                        .invalidValue(exception.getInvalidValue())
                        .build()
                ).toList();
        response.details().put("errors", additionalMessages);

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        HttpStatus status = BAD_REQUEST;
        ErrorMessageResponse response = getErrorMessageResponse(e, status);
        response.details().put("errors", e.getAllErrors());

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<MessageResponse> badRequestExceptionHandler(BadRequestException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(exception.getMessage()));
    }

    @ExceptionHandler(value = JwtAuthenticationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public com.khomsi.grid.main.handler.ErrorMessage handleTokenRefreshException(JwtAuthenticationException ex,
                                                                                 WebRequest request) {
        return new com.khomsi.grid.main.handler.ErrorMessage(
                HttpStatus.FORBIDDEN.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
    }

    private ErrorMessageResponse getErrorMessageResponse(Throwable e, HttpStatus status) {
        return ErrorMessageResponse.builder()
                .type(MessageType.VALIDATION_ERROR)
                .createdAt(Instant.now())
                .statusCode(status)
                .status(status.value())
                .message(e.getMessage())
                .details(new HashMap<>())
                .build();
    }

    @Builder
    private record ErrorMessage(
            String message,
            Object invalidValue

    ) {
    }
}
