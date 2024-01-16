package com.khomsi.backend.main.handler.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

public class GlobalServiceException extends ResponseStatusException {
    public final Map<String, Object> details = new HashMap<>();

    public GlobalServiceException(HttpStatusCode status) {
        super(status);
    }

    public GlobalServiceException(HttpStatusCode status, Map<String, Object> details) {
        super(status);
        this.details.putAll(details);
    }

    public GlobalServiceException(HttpStatusCode status, String reason, Throwable cause) {
        super(status, reason, cause);
    }

    public GlobalServiceException(HttpStatusCode status, String reason) {
        super(status, reason);
    }

    public GlobalServiceException(HttpStatusCode status, String reason, Map<String, Object> details) {
        super(status, reason);
        this.details.putAll(details);
    }
}