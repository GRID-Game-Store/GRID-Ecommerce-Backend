package com.khomsi.grid.main.authentication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ApiRequestException extends RuntimeException {
    public ApiRequestException(String message) {
        super(message);
    }
}
