package com.khomsi.backend.main.game.service;

import com.khomsi.backend.main.game.model.dto.*;
import com.khomsi.backend.main.game.model.entity.Game;

import java.util.List;

public interface GameService {

    //TODO Write integration tests with cucumber for this endpoint
    GeneralGame getExtendedGamesByPage(GameCriteria gameCriteria, boolean applyActiveFilter);

    List<GameModelWithGenreLimit> getGamesByGenre(int qty, String genre);

    List<PopularGameModel> getPopularQtyOfGames(int gameQuantity);

    List<GameModelWithGenreLimit> getRandomQtyOfGames(int gameQuantity);

    Game getActiveGameById(Long gameId);

    Game getGameById(Long gameId);

    ExtendedGame getExtendedGameById(Long gameId);

    List<PopularGameModel> getSpecialOffers(String query, int qty);

    List<GameModelWithGenreLimit> searchGamesByTitle(String text, int qty);
}
