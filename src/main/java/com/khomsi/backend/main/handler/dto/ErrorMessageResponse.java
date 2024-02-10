package com.khomsi.backend.main.handler.dto;

import com.khomsi.backend.main.handler.MessageType;
import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.Map;

@Builder
public record ErrorMessageResponse(Instant createdAt, MessageType type,
                                   HttpStatus statusCode, int status,
                                   String message, Map<String, Object> details) {
}
