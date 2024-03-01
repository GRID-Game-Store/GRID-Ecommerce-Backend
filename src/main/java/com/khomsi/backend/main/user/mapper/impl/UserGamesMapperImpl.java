package com.khomsi.backend.main.user.mapper.impl;

import com.khomsi.backend.main.game.mapper.GameMapper;
import com.khomsi.backend.main.user.mapper.UserGamesMapper;
import com.khomsi.backend.main.user.model.dto.UserShortGamesDTO;
import com.khomsi.backend.main.user.model.entity.UserGames;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserGamesMapperImpl implements UserGamesMapper {
    private final GameMapper gameMapper;
    @Override
    public UserShortGamesDTO toUserShortGame(UserGames games) {
        return UserShortGamesDTO.builder()
                .game(gameMapper.toShortGame(games.getGame()))
                .playtime(games.getPlaytime())
                .purchaseDate(games.getPurchaseDate())
                .build();
    }
}
