package com.khomsi.backend.main.user.mapper;

import com.khomsi.backend.main.user.model.dto.ShortUserInfoDTO;
import com.khomsi.backend.main.user.model.dto.UserShortGamesDTO;
import com.khomsi.backend.main.user.model.entity.UserGames;
import com.khomsi.backend.main.user.model.entity.UserInfo;

public interface UserInfoMapper {
    ShortUserInfoDTO toShortUserInfoDTO(UserInfo userInfo);

    UserShortGamesDTO toUserShortGame(UserGames games);
}
