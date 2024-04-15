package com.khomsi.backend.main.game.model.dto;

import com.khomsi.backend.main.game.model.entity.Game;

public record ExtendedGame(Game game, boolean ownedByCurrentUser) {
}
