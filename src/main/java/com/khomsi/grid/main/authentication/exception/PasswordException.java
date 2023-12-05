package com.khomsi.grid.main.authentication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PasswordException extends RuntimeException {
    public PasswordException(String message) {
        super(message);
    }
}
