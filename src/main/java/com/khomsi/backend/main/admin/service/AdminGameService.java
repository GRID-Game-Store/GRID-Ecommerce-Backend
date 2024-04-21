package com.khomsi.backend.main.admin.service;

import com.khomsi.backend.main.admin.model.request.GameRequest;
import com.khomsi.backend.main.admin.model.response.AdminResponse;
import com.khomsi.backend.main.game.model.dto.GameCriteria;
import com.khomsi.backend.main.game.model.dto.GameModelWithGenreLimit;
import com.khomsi.backend.main.game.model.dto.GeneralGame;

import java.util.List;

public interface AdminGameService {
    AdminResponse addGameToDb(GameRequest gameRequest);

    AdminResponse editGame(Long gameId, GameRequest gameRequest);

    List<GameModelWithGenreLimit> searchGamesByTitleWithoutActiveCheck(String text, int qty);

    GeneralGame getExtendedGamesByPageForAdmin(GameCriteria gameCriteria);

    AdminResponse toggleGameActiveStatus(Long gameId, boolean active);
}
