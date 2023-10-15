package com.khomsi.grid.main.game.service;

import com.khomsi.grid.main.game.GameRepository;
import com.khomsi.grid.main.game.handler.exception.GameNotFoundException;
import com.khomsi.grid.main.game.model.dto.GeneralGame;
import com.khomsi.grid.main.game.model.entity.Game;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
@Slf4j
public record GameServiceImpl(GameRepository gameRepository)
        implements GameService {

    @Override
    public GeneralGame showGamesByPage(int page, int pageSize, String[] sort, String title) {
        Sort sorting = Sort.by(sort[1].equalsIgnoreCase("asc") ? ASC : DESC, sort[0]);
        Pageable pagingSort = PageRequest.of(page, pageSize, sorting);
        Page<Game> gamePage = title == null
                ? gameRepository.findAll(pagingSort)
                : gameRepository.findGameByTitleContainingIgnoreCase(title, pagingSort);
        if (gamePage.isEmpty()) {
            throw new GameNotFoundException();
        }
        return GeneralGame.builder()
                .games(gamePage.getContent())
                .totalItems(gamePage.getTotalElements())
                .totalPages(gamePage.getTotalPages())
                .currentPage(page)
                .build();
    }
}