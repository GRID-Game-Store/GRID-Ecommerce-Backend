package com.khomsi.backend.main.user.mapper.impl;

import com.khomsi.backend.main.game.mapper.GameMapper;
import com.khomsi.backend.main.user.mapper.UserInfoMapper;
import com.khomsi.backend.main.user.model.dto.ShortUserInfoDTO;
import com.khomsi.backend.main.user.model.dto.UserShortGamesDTO;
import com.khomsi.backend.main.user.model.entity.UserGames;
import com.khomsi.backend.main.user.model.entity.UserInfo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserInfoMapperImpl implements UserInfoMapper {
    private final GameMapper gameMapper;

    @Override
    public ShortUserInfoDTO toShortUserInfoDTO(UserInfo userInfo) {
        return ShortUserInfoDTO.builder()
                .externalId(userInfo.getExternalId())
                .email(userInfo.getEmail())
                .balance(userInfo.getBalance())
                .build();
    }

    @Override
    public UserShortGamesDTO toUserShortGame(UserGames games) {
        return UserShortGamesDTO.builder()
                .game(gameMapper.toShortGame(games.getGame()))
                .playtime(games.getPlaytime())
                .purchaseDate(games.getPurchaseDate())
                .build();
    }
}
