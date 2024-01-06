package com.khomsi.grid.main.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User with such credentials is not found");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}