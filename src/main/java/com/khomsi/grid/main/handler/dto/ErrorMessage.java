package com.khomsi.grid.main.handler.dto;

import lombok.Builder;

@Builder
public record ErrorMessage(
        String message,
        Object invalidValue

) {
}