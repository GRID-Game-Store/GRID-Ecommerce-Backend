package com.khomsi.backend.main.admin.service;

import com.khomsi.backend.main.admin.model.dto.GameDTO;
import com.khomsi.backend.main.admin.model.response.AdminResponse;
import com.khomsi.backend.main.game.model.dto.GameCriteria;
import com.khomsi.backend.main.game.model.dto.GeneralGame;
import com.khomsi.backend.main.game.model.entity.Game;
import org.springframework.transaction.annotation.Transactional;

public interface AdminService {
    AdminResponse addGameToDb(GameDTO gameDTO);

    AdminResponse editGame(Long gameId, GameDTO gameDTO);

    Game getInvisibleGameById(Long gameId);

    GeneralGame getExtendedGamesByPageForAdmin(GameCriteria gameCriteria);

    AdminResponse toggleGameActiveStatus(Long gameId, boolean active);
}
