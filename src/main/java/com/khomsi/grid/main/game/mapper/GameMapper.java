package com.khomsi.grid.main.game.mapper;

import com.khomsi.grid.main.game.model.dto.GameModelWithGenreLimit;
import com.khomsi.grid.main.game.model.dto.PopularGameModel;
import com.khomsi.grid.main.game.model.dto.ShortGameModel;
import com.khomsi.grid.main.game.model.entity.Game;

public interface GameMapper {
    ShortGameModel toShortGame(Game game);

    PopularGameModel toPopularGame(Game game);

    GameModelWithGenreLimit toLimitGenreGame(Game game);
}
