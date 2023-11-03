package com.khomsi.grid.main.game.service;

import com.khomsi.grid.main.game.model.dto.GeneralGame;
import com.khomsi.grid.main.game.model.dto.ShortGameModel;
import com.khomsi.grid.main.game.model.entity.Game;

import java.util.List;

public interface GameService {
    GeneralGame getGamesByPage(int page, int pageSize,
                               String[] sort, String title);

    List<ShortGameModel> getPopularQtyOfGames(int gameQuantity);

    //Todo extend the method
    Game getGameById(Long gameId);
}
