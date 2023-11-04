package com.khomsi.grid.main.game.service;

import com.khomsi.grid.main.game.GameRepository;
import com.khomsi.grid.main.game.handler.exception.GameNotFoundException;
import com.khomsi.grid.main.game.mapper.GameMapper;
import com.khomsi.grid.main.game.model.dto.GeneralGame;
import com.khomsi.grid.main.game.model.dto.ShortGameModel;
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
        List<ShortGameModel> shortGameModels = gamePage
                .map(gameMapper::toShortGame)
                .getContent();

        return GeneralGame.builder()
                .games(shortGameModels)
                .totalItems(gamePage.getTotalElements())
                .totalPages(gamePage.getTotalPages())
                .currentPage(page)
                .build();
    }


    @Override
    public List<ShortGameModel> getPopularQtyOfGames(int gameQuantity) {
        List<ShortGameModel> shortGameModels = gameRepository.findAll().stream()
                .map(gameMapper::toShortGame)
                .toList();
        return (shortGameModels.size() <= gameQuantity) ? shortGameModels :
                new Random().ints(0, shortGameModels.size())
                        .distinct()
                        .limit(gameQuantity)
                        .mapToObj(shortGameModels::get).toList();
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