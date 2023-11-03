package com.khomsi.grid.main.game.service;

import com.khomsi.grid.main.game.GameRepository;
import com.khomsi.grid.main.game.handler.exception.GameNotFoundException;
import com.khomsi.grid.main.game.mapper.GameMapper;
import com.khomsi.grid.main.game.model.dto.GeneralGame;
import com.khomsi.grid.main.game.model.dto.MainGameModel;
import com.khomsi.grid.main.game.model.entity.Game;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.Direction.*;

@Service
@Slf4j
public record GameServiceImpl(GameRepository gameRepository, GameMapper gameMapper)
        implements GameService {
    @Override
    public GeneralGame getGamesByPage(int page, int pageSize, String[] sort, String title) {
        Sort sorting = createSorting(sort);
        Pageable pagingSort = PageRequest.of(page, pageSize, sorting);
        Page<Game> gamePage = title == null
                ? gameRepository.findAll(pagingSort)
                : gameRepository.findGameByTitleContainingIgnoreCase(title, pagingSort);

        if (gamePage.isEmpty()) {
            throw new GameNotFoundException();
        }
        List<MainGameModel> mainGameModels = gamePage
                .map(gameMapper::toMainGames)
                .getContent();

        return GeneralGame.builder()
                .games(mainGameModels)
                .totalItems(gamePage.getTotalElements())
                .totalPages(gamePage.getTotalPages())
                .currentPage(page)
                .build();
    }


    @Override
    public List<MainGameModel> getPopularQtyOfGames(int gameQuantity) {
        List<MainGameModel> mainGameModels = gameRepository.findAll().stream()
                .map(gameMapper::toMainGames)
                .toList();
        return (mainGameModels.size() <= gameQuantity) ? mainGameModels :
                new Random().ints(0, mainGameModels.size())
                        .distinct()
                        .limit(gameQuantity)
                        .mapToObj(mainGameModels::get).toList();
    }

    @Override
    public Game getGameById(Long gameId) {
        return gameRepository.findById(gameId).orElseThrow(GameNotFoundException::new);
    }

    private Sort createSorting(String[] sort) {
        if (sort != null && sort.length == 2) {
            return Sort.by(sort[1].equalsIgnoreCase("asc") ? ASC : DESC, sort[0]);
        } else {
            return Sort.by(DESC, "id");
        }
    }
}