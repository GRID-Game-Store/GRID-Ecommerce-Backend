package com.khomsi.backend.main.game.service;

import com.khomsi.backend.additional.genre.model.entity.Genre;
import com.khomsi.backend.main.game.GameRepository;
import com.khomsi.backend.main.game.mapper.GameMapper;
import com.khomsi.backend.main.game.model.dto.*;
import com.khomsi.backend.main.game.model.entity.Game;
import com.khomsi.backend.main.handler.exception.GlobalServiceException;
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

    //    public GeneralGame getExtendedGamesByPage(GameCriteria gameCriteria) {
//        int page = gameCriteria.getPage();
//
//        Sort sorting = createSorting(gameCriteria.getSort());
//        Pageable pagingSort = PageRequest.of(page, gameCriteria.getSize(), sorting);
//
//        // Создаем спецификацию на основе списка тегов
//        Specification<Game> tagSpecification = GameSpecifications.byIds(GameSpecifications.parseIds(gameCriteria.getTags()));
//
//        // Комбинируем спецификации, включая другие критерии, если необходимо
//        Specification<Game> combinedSpecification = Specification.where(tagSpecification)
//                .and(GameSpecifications.byTitle(gameCriteria.getTitle()))
//                .and(GameSpecifications.byMaxPrice(gameCriteria.getMaxPrice()));
//
//        // Получаем страницу игр с учетом всех критериев
//        Page<Game> gamePage = gameRepository.findAll(combinedSpecification, pagingSort);
//
//        if (gamePage.isEmpty()) {
//            throw new GlobalServiceException(HttpStatus.NOT_FOUND, "Games are not found in the database.");
//        }
//
//        List<ShortGameModel> shortGameModels = gamePage
//                .map(gameMapper::toShortGame)
//                .getContent();
//
//        return GeneralGame.builder()
//                .games(shortGameModels)
//                .totalItems(gamePage.getTotalElements())
//                .totalPages(gamePage.getTotalPages())
//                .currentPage(page)
//                .build();
//    }
    @Override
    public GeneralGame getExtendedGamesByPage(GameCriteria gameCriteria) {
        int page = gameCriteria.getPage();

        Sort sorting = createSorting(gameCriteria.getSort());
        Pageable pagingSort = PageRequest.of(page, gameCriteria.getSize(), sorting);
        //Я вставляю теги и нету четкого понятия сколько игр должно выйти, после того, как переход происходит на
        // новую страницу, количество товаров увеличивается. Так же выводит игры, которые выводит не по тем тегам, которые указаны в самих
        // тегах. Тоесть они фактически не работают. Так же с ценой и так далее.
        Specification<Game> specification = Specification.where(null);
        specification = specification
                .and(GameSpecifications.byTitle(gameCriteria.getTitle()));
        specification = specification
                .and(GameSpecifications.byMaxPrice(gameCriteria.getMaxPrice()));

//        specification = specification.and(GameSpecifications.byTagsIds(GameSpecifications.parseIds(gameCriteria.getTags())));
//        specification = specification.and(GameSpecifications.byGenres(GameSpecifications.parseIds(gameCriteria.getGenres())));
//        specification = specification.and(GameSpecifications.byTagsIds(GameSpecifications.parseIds(gameCriteria.getPlatforms()), "platforms"));
//        specification = specification.and(GameSpecifications.byTagsIds(GameSpecifications.parseIds(gameCriteria.getDevelopers()), "developer"));
//        specification = specification.and(GameSpecifications.byTagsIds(GameSpecifications.parseIds(gameCriteria.getPublishers()), "publisher"));

//        specification = specification
//                .and(GameSpecifications.byIds(GameSpecifications.parseIds(gameCriteria.getGenres()), "genres"));
//        specification = specification
//                .and(GameSpecifications.byIds(GameSpecifications.parseIds(gameCriteria.getPlatforms()), "platforms"));
//        specification = specification
//                .and(GameSpecifications.byIds(GameSpecifications.parseIds(gameCriteria.getTags())));
//        specification = specification
//                .and(GameSpecifications.byIds(GameSpecifications.parseIds(gameCriteria.getDevelopers()), "developer"));
//        specification = specification
//                .and(GameSpecifications.byIds(GameSpecifications.parseIds(gameCriteria.getPublishers()), "publisher"));

        Page<Game> gamePage = gameRepository.findAll(specification, pagingSort);
        log.info("gamepage={}", gamePage);
        if (gamePage.isEmpty()) {
            throw new GlobalServiceException(HttpStatus.NOT_FOUND, "Games are not found in the database.");
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
                .map(gameMapper::toLimitGenreGame)
                .toList();
    }

    @Override
    public List<PopularGameModel> getPopularQtyOfGames(int gameQuantity) {
        List<PopularGameModel> shortGameModels = gameRepository.findAll().stream()
                .map(gameMapper::toPopularGame)
                .toList();
        return getRandomGames(shortGameModels, gameQuantity);
    }

    @Override
    public List<GameModelWithGenreLimit> getRandomQtyOfGames(int gameQuantity) {
        List<GameModelWithGenreLimit> shortGameModels = gameRepository.findAll().stream()
                .map(gameMapper::toLimitGenreGame)
                .toList();
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
                .map(gameMapper::toPopularGame)
                .limit(qty)
                .toList();
    }

    @Override
    public List<GameModelWithGenreLimit> searchGamesByTitle(String text, int qty) {
        return gameRepository.findSimilarTitles(transformWord(text)).stream()
                .map(gameMapper::toLimitGenreGame)
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