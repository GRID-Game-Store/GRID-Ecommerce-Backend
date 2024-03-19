package com.khomsi.backend.main.checkout.model.dto;

import com.khomsi.backend.main.game.model.entity.Game;
import lombok.Builder;

import java.util.List;
@Builder
public record Mail(String recipientEmail, List<Game> games,
                   String paymentMethod) {
}