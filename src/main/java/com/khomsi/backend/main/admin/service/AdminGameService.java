package com.khomsi.backend.main.admin.service;

import com.khomsi.backend.main.admin.model.request.GameRequest;
import com.khomsi.backend.main.admin.model.response.AdminResponse;
import com.khomsi.backend.main.game.model.dto.GameCriteria;
import com.khomsi.backend.main.game.model.dto.GameModelWithGenreLimit;
import com.khomsi.backend.main.game.model.dto.GeneralGame;
import com.khomsi.backend.main.game.model.entity.Game;

import java.util.List;

public interface AdminGameService {
    AdminResponse addGameToDb(GameRequest gameRequest);

    AdminResponse editGame(Long gameId, GameRequest gameRequest);

    Game getInvisibleGameById(Long gameId);

    List<GameModelWithGenreLimit> searchGamesByTitleWithoutActiveCheck(String text, int qty);

    AdminResponse deleteGame(Long gameId);

    GeneralGame getExtendedGamesByPageForAdmin(GameCriteria gameCriteria);

    AdminResponse toggleGameActiveStatus(Long gameId, boolean active);
}
