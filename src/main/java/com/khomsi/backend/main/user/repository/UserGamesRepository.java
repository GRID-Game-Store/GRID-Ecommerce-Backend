package com.khomsi.backend.main.user.repository;


import com.khomsi.backend.main.user.model.entity.UserGames;
import com.khomsi.backend.main.user.model.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserGamesRepository extends JpaRepository<UserGames, String> {
    List<UserGames> findAllByUserOrderByPurchaseDateDesc(UserInfo userInfo);
}
