package com.khomsi.grid.main.game.service;

import com.khomsi.grid.main.game.model.dto.GameModelWithLimit;
import com.khomsi.grid.main.game.model.dto.GeneralGame;
import com.khomsi.grid.main.game.model.dto.PopularGameModel;
import com.khomsi.grid.main.game.model.entity.Game;

import java.util.List;

public interface GameService {
    GeneralGame getGamesByPage(int page, int pageSize,
                               String[] sort, String title);

    List<GameModelWithLimit> getGamesByGenre(int qty, String genre);

    List<PopularGameModel> getPopularQtyOfGames(int gameQuantity);

    List<GameModelWithLimit> getRandomQtyOfGames(int gameQuantity);

    Game getGameById(Long gameId);
}
