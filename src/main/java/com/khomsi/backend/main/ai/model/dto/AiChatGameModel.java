package com.khomsi.backend.main.ai.model.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.StringJoiner;

@Builder
public record AiChatGameModel(
        Long id,
        String title,
        BigDecimal price
) {
    @Override
    public String toString() {
        return new StringJoiner(", ", "Game" + "[", "]")
                .add("id: " + id)
                .add("title: '" + title + "'")
                .add("price: " + price)
                .toString();
    }
}
