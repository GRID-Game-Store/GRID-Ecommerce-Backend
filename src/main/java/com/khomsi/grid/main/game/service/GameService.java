package com.khomsi.grid.main.game.service;

import com.khomsi.grid.main.game.model.dto.GeneralGame;

public interface GameService {
    GeneralGame showGamesByPage(int page, int pageSize,
                                String[] sort, String title);
}
