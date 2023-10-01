package com.khomsi.grid.main.game.service;

import com.khomsi.grid.main.game.GameRepository;
import com.khomsi.grid.main.game.model.dto.GeneralGame;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public record GameServiceImpl(GameRepository gameRepository)
        implements GameService {

    @Override
    public GeneralGame showGamesByPage(int page, int pageSize) {
        return GeneralGame.builder()
                .games(gameRepository.findAll(PageRequest.of(page, pageSize))).build();
    }
}
