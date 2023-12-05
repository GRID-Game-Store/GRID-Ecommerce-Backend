package com.khomsi.grid.main.authentication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class EmailException extends RuntimeException {
    public EmailException(String message) {
        super(message);
    }
}
