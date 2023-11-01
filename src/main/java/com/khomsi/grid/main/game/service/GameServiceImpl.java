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

import java.util.List;
import java.util.Random;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
@Slf4j
public record GameServiceImpl(GameRepository gameRepository)
        implements GameService {

    @Override
    public GeneralGame getGamesByPage(int page, int pageSize, String[] sort, String title) {
        Sort sorting;
        if (sort != null && sort.length == 2) {
            sorting = Sort.by(sort[1].equalsIgnoreCase("asc") ? ASC : DESC, sort[0]);
        } else {
            sorting = Sort.by(DESC, "id");
        }
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

    @Override
    public List<Game> getRandomQtyOfGames(int gameQuantity) {
        List<Game> games = gameRepository.findAll();
        return (games.size() <= gameQuantity) ? games :
                new Random().ints(0, games.size())
                        .distinct()
                        .limit(gameQuantity)
                        .mapToObj(games::get).toList();
    }

    @Override
    public Game getGameById(Long gameId) {
        return gameRepository.findById(gameId).orElseThrow(GameNotFoundException::new);
    }
}