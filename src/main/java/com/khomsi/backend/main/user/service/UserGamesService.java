package com.khomsi.backend.main.user.service;

import com.khomsi.backend.main.checkout.model.entity.Transaction;
import com.khomsi.backend.main.game.model.entity.Game;
import com.khomsi.backend.main.user.model.dto.UserShortGamesDTO;
import com.khomsi.backend.main.user.model.entity.UserGames;
import com.khomsi.backend.main.user.model.entity.UserInfo;

import java.util.List;

public interface UserGamesService {
    List<UserShortGamesDTO> getAllUserGames();

    void getGamesFromTransactionToLibrary(UserInfo user, Transaction transaction);

    Boolean checkIfGameExists(UserInfo userInfo, Game game);

    UserGames createUserGames(UserInfo user, Game game);

    void saveUserGames(UserInfo user, Game game);
}
