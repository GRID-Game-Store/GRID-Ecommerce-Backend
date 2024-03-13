package com.khomsi.backend.main.game.service;

import com.khomsi.backend.additional.genre.model.entity.Genre;
import com.khomsi.backend.main.game.GameRepository;
import com.khomsi.backend.main.game.mapper.GameMapper;
import com.khomsi.backend.main.game.model.dto.*;
import com.khomsi.backend.main.game.model.entity.Game;
import com.khomsi.backend.main.handler.exception.GlobalServiceException;
import com.khomsi.backend.main.user.service.UserInfoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
@Slf4j
@AllArgsConstructor
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final GameMapper gameMapper;
    private final UserInfoService userInfoService;

    //TODO Write integration tests with cucumber for this endpoint
    @Override
    public GeneralGame getExtendedGamesByPage(GameCriteria gameCriteria) {
        int page = gameCriteria.getPage();

        Sort sorting = createSorting(gameCriteria.getSort());
        Pageable pagingSort = PageRequest.of(page, gameCriteria.getSize(), sorting);
        Specification<Game> specification = Specification.where(null);
        specification = specification.and(GameSpecifications.byTitle(gameCriteria.getTitle()));
        specification = specification.and(GameSpecifications.byMaxPrice(gameCriteria.getMaxPrice()));
        specification = specification.and(GameSpecifications.byTagIds(gameCriteria.getTags()));
        specification = specification.and(GameSpecifications.byField("genres",
                "name", gameCriteria.getGenres()));
        specification = specification.and(GameSpecifications.byField("platforms",
                "name", gameCriteria.getPlatforms()));
        specification = specification.and(GameSpecifications.byField("developer",
                "name", gameCriteria.getDevelopers()));
        specification = specification.and(GameSpecifications.byField("publisher",
                "name", gameCriteria.getPublishers()));

        Page<Game> gamePage = gameRepository.findAll(specification, pagingSort);
        if (gamePage.isEmpty()) {
            throw new GlobalServiceException(HttpStatus.NOT_FOUND, "Games are not found in the database.");
        }
        List<ShortGameModel> shortGameModels = gamePage
                .map(game -> {
                    boolean ownedByCurrentUser = userInfoService.checkIfGameIsOwnedByCurrentUser(game);
                    return gameMapper.toShortGame(game, ownedByCurrentUser);
                }).getContent();

        return GeneralGame.builder()
                .games(shortGameModels)
                .totalItems(gamePage.getTotalElements())
                .totalPages(gamePage.getTotalPages() - 1)
                .currentPage(page)
                .build();
    }

    @Override
    public List<GameModelWithGenreLimit> getGamesByGenre(int qty, String excludedGenre) {
        return gameRepository.findGamesByGenre(excludedGenre).stream()
                .filter(game -> {
                    Set<Genre> genres = game.getGenres();
                    if (genres.size() > 2) {
                        genres.removeIf(genre -> genre.getName().equals(excludedGenre));
                    }
                    return genres.size() <= 2;
                })
                .limit(qty)
                .map(game -> {
                    boolean ownedByCurrentUser = userInfoService.checkIfGameIsOwnedByCurrentUser(game);
                    return gameMapper.toLimitGenreGame(game, ownedByCurrentUser);
                }).toList();
    }

    @Override
    public List<PopularGameModel> getPopularQtyOfGames(int gameQuantity) {
        List<PopularGameModel> shortGameModels = gameRepository.findAll().stream()
                .map(game -> {
                    boolean ownedByCurrentUser = userInfoService.checkIfGameIsOwnedByCurrentUser(game);
                    return gameMapper.toPopularGame(game, ownedByCurrentUser);
                }).toList();
        return getRandomGames(shortGameModels, gameQuantity);
    }

    @Override
    public List<GameModelWithGenreLimit> getRandomQtyOfGames(int gameQuantity) {
        List<GameModelWithGenreLimit> shortGameModels = gameRepository.findAll().stream()
                .map(game -> {
                    boolean ownedByCurrentUser = userInfoService.checkIfGameIsOwnedByCurrentUser(game);
                    return gameMapper.toLimitGenreGame(game, ownedByCurrentUser);
                }).toList();
        return getRandomGames(shortGameModels, gameQuantity);
    }

    @Override
    public Game getGameById(Long gameId) {
        return gameRepository.findById(gameId).orElseThrow(() ->
                new GlobalServiceException(HttpStatus.NOT_FOUND, "Game with id " + gameId + " is not found."));
    }

    @Override
    public List<PopularGameModel> getSpecialOffers(String query, int qty) {
        //TODO refactor the method in future
        List<Game> games;
        switch (query) {
            case "release date" -> games = gameRepository.findGamesByEarliestReleaseDate();
            case "sales" -> games = gameRepository.findGamesWithDiscount();
            //TODO no metrics yet to use it not as a random
            case "discount" -> games = getRandomGames(gameRepository.findAll(), qty);
            default -> throw new GlobalServiceException(HttpStatus.NOT_FOUND, "Games are not found in database.");
        }
        return games.stream()
                .map(game -> {
                    boolean ownedByCurrentUser = userInfoService.checkIfGameIsOwnedByCurrentUser(game);
                    return gameMapper.toPopularGame(game, ownedByCurrentUser);
                })
                .limit(qty)
                .toList();
    }

    @Override
    public List<GameModelWithGenreLimit> searchGamesByTitle(String text, int qty) {
        return gameRepository.findSimilarTitles(transformWord(text)).stream()
                .map(game -> {
                    boolean ownedByCurrentUser = userInfoService.checkIfGameIsOwnedByCurrentUser(game);
                    return gameMapper.toLimitGenreGame(game, ownedByCurrentUser);
                })
                .limit(qty)
                .toList();
    }


    private String transformWord(String word) {
        return word.chars()
                .mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.joining("%", "", "%"));
    }

    private <T> List<T> getRandomGames(List<T> gameModels, int gameQuantity) {
        return (gameModels.size() <= gameQuantity) ? gameModels :
                new Random().ints(0, gameModels.size())
                        .distinct()
                        .limit(gameQuantity)
                        .mapToObj(gameModels::get).toList();
    }

    private Sort createSorting(String[] sort) {
        return (sort != null && sort.length == 2) ?
                Sort.by(sort[1].equalsIgnoreCase("asc") ? ASC : DESC, sort[0]) :
                Sort.by(DESC, "id");
    }
}