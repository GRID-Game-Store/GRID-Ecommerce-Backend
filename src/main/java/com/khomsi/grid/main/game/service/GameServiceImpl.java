package com.khomsi.grid.main.game.service;

import com.khomsi.grid.main.game.GameRepository;
import com.khomsi.grid.main.game.handler.exception.GameNotFoundException;
import com.khomsi.grid.main.game.model.dto.GeneralGame;
import com.khomsi.grid.main.game.model.entity.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public record GameServiceImpl(GameRepository gameRepository)
        implements GameService {

    @Override
    public GeneralGame showGamesByPage(int page, int pageSize) {
        page = Math.max(1, page);
        Page<Game> gamePage = gameRepository.findAll(PageRequest.of(page - 1, pageSize));
        if (gamePage.isEmpty())
            throw new GameNotFoundException();

        return GeneralGame.builder()
                .games(gamePage.getContent())
                .totalItems(gamePage.getTotalElements())
                .totalPages(gamePage.getTotalPages())
                .currentPage(gamePage.getNumber())
                .build();
    }
}
