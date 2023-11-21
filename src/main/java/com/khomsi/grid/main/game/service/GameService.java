package com.khomsi.grid.main.game.service;

import com.khomsi.grid.main.game.model.dto.GameModelWithGenreLimit;
import com.khomsi.grid.main.game.model.dto.GeneralGame;
import com.khomsi.grid.main.game.model.dto.PopularGameModel;
import com.khomsi.grid.main.game.model.entity.Game;

import java.util.List;

public interface GameService {
    GeneralGame getGamesByPage(int page, int pageSize,
                               String[] sort, String title);

    List<GameModelWithGenreLimit> getGamesByGenre(int qty, String genre);

    List<PopularGameModel> getPopularQtyOfGames(int gameQuantity);

    List<GameModelWithGenreLimit> getRandomQtyOfGames(int gameQuantity);

    Game getGameById(Long gameId);

    List<GameModelWithGenreLimit> getSpeacialOffers(String query, int qty);
}
