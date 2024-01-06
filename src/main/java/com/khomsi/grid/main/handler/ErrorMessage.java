package com.khomsi.grid.main.handler;

import java.util.Date;

//TODO change this
public record ErrorMessage(int statusCode,
                           Date timestamp,
                           String message,
                           String description
) {
}
