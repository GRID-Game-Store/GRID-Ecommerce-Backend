package com.khomsi.backend.main.user.service.impl;

import com.khomsi.backend.main.checkout.model.entity.Transaction;
import com.khomsi.backend.main.checkout.model.entity.TransactionGames;
import com.khomsi.backend.main.game.model.entity.Game;
import com.khomsi.backend.main.user.mapper.UserGamesMapper;
import com.khomsi.backend.main.user.model.dto.UserShortGamesDTO;
import com.khomsi.backend.main.user.model.entity.UserGames;
import com.khomsi.backend.main.user.model.entity.UserInfo;
import com.khomsi.backend.main.user.repository.UserGamesRepository;
import com.khomsi.backend.main.user.service.UserGamesService;
import com.khomsi.backend.main.user.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserGamesServiceImpl implements UserGamesService {
    private final UserGamesRepository userGamesRepository;
    private final UserInfoService userInfoService;
    private final UserGamesMapper userGamesMapper;

    @Override
    public List<UserShortGamesDTO> getAllUserGames() {
        //TODO in future add filter and sorting
        UserInfo user = userInfoService.getUserInfo();
        List<UserGames> userGames = userGamesRepository.findAllByUserOrderByPurchaseDateDesc(user);
        return userGames.stream()
                .map(userGamesMapper::toUserShortGame)
                .collect(Collectors.toList());
    }

    @Override
    public void getGamesFromTransactionToLibrary(UserInfo user, Transaction transaction) {
        List<TransactionGames> transactionGamesList = transaction.getTransactionGames();
        Set<UserGames> userGames = new LinkedHashSet<>();
        transactionGamesList.forEach(transactionGames -> userGames.add(createUserGames(user, transactionGames.getGame())));
        userGamesRepository.saveAll(userGames);
        user.setUserGames(userGames);
    }

    @Override
    public Boolean checkIfGameExists(UserInfo userInfo, Game game) {
        Set<UserGames> userGames = userInfo.getUserGames();
        return userGames.stream()
                .anyMatch(userGame -> userGame.getGame().equals(game));
    }

    @Override
    public UserGames createUserGames(UserInfo user, Game game) {
        UserGames userGames = new UserGames();
        userGames.setUser(user);
        userGames.setGame(game);
        userGames.setPurchaseDate(Instant.now());
        userGames.setPlaytime(LocalTime.of(0, 0));
        return userGames;
//        // Set other fields in future for user
    }

    @Override
    public void saveUserGames(UserInfo user, Game game) {
        userGamesRepository.save(createUserGames(user, game));
    }
}