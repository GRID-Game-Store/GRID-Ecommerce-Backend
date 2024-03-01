package com.khomsi.backend.main.user.mapper;

import com.khomsi.backend.main.user.model.dto.UserShortGamesDTO;
import com.khomsi.backend.main.user.model.entity.UserGames;

public interface UserGamesMapper {
    UserShortGamesDTO toUserShortGame(UserGames games);
}
