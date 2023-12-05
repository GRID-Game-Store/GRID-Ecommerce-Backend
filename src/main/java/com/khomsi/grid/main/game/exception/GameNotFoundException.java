package com.khomsi.grid.main.game.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class GameNotFoundException extends RuntimeException {
    public GameNotFoundException() {
        super("Games weren't found.");
    }
}
