package com.khomsi.backend.main.admin.service.impl;

import com.khomsi.backend.additional.developer.model.entity.Developer;
import com.khomsi.backend.additional.developer.service.DeveloperService;
import com.khomsi.backend.additional.genre.GenreRepository;
import com.khomsi.backend.additional.media.GameMediaRepository;
import com.khomsi.backend.additional.media.model.entity.GameMedia;
import com.khomsi.backend.additional.platform.PlatformRepository;
import com.khomsi.backend.additional.publisher.model.entity.Publisher;
import com.khomsi.backend.additional.publisher.service.PublisherService;
import com.khomsi.backend.additional.tag.TagRepository;
import com.khomsi.backend.main.admin.model.request.GameRequest;
import com.khomsi.backend.main.admin.model.response.AdminResponse;
import com.khomsi.backend.main.admin.service.AdminGameService;
import com.khomsi.backend.main.game.GameRepository;
import com.khomsi.backend.main.game.mapper.GameMapper;
import com.khomsi.backend.main.game.model.dto.GameCriteria;
import com.khomsi.backend.main.game.model.dto.GameModelWithGenreLimit;
import com.khomsi.backend.main.game.model.dto.GeneralGame;
import com.khomsi.backend.main.game.model.entity.Game;
import com.khomsi.backend.main.game.service.GameService;
import com.khomsi.backend.main.handler.exception.GlobalServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminGameServiceImpl implements AdminGameService {
    private final DeveloperService developerService;
    private final PublisherService publisherService;
    private final TagRepository tagRepository;
    private final GenreRepository genreRepository;
    private final PlatformRepository platformRepository;
    private final GameService gameService;
    private final GameRepository gameRepository;
    private final GameMediaRepository gameMediaRepository;
    private final GameMapper gameMapper;

    @Override
    @Transactional
    public AdminResponse addGameToDb(GameRequest gameRequest) {
        // Check if the game with the same title already exists
        if (gameRepository.existsGameByTitleIgnoreCase(gameRequest.title())) {
            throw new GlobalServiceException(HttpStatus.BAD_REQUEST,
                    "Game with title '" + gameRequest.title() + "' already exists");
        }
        // Create a new game entities
        Game game = buildGameEntityFromDTO(new Game(), new GameMedia(), gameRequest);
        // Save game in repository
        game = gameRepository.save(game);
        return AdminResponse.builder()
                .response("Game with id " + game.getId() + " was added!")
                .build();
    }

    @Transactional
    @Override
    public AdminResponse editGame(Long gameId, GameRequest gameRequest) {
        Game game = gameService.getGameById(gameId);
        // Edit entities with new data fields.
        buildGameEntityFromDTO(game, game.getGameMedia(), gameRequest);
        // Save the updated game entities
        game = gameRepository.save(game);
        return AdminResponse.builder()
                .response("Game with id " + game.getId() + " was edited!")
                .build();
    }

    // Helper method to build a Game entities from DTO
    private Game buildGameEntityFromDTO(Game game, GameMedia gameMedia, GameRequest gameRequest) {
        game.setTitle(gameRequest.title());
        game.setDescription(gameRequest.description());
        game.setReleaseDate(gameRequest.releaseDate());
        game.setSystemRequirements(gameRequest.systemRequirements());
        game.setAboutGame(gameRequest.aboutGame());
        game.setPrice(gameRequest.price());
        game.setActive(gameRequest.active());
        game.setDiscount(gameRequest.discount());
        game.setPermitAge(gameRequest.permitAge().getValue());
        game.setCoverImageUrl(gameRequest.coverImageUrl());

        // Set developer and publisher
        Developer developer = developerService.findDeveloperById(gameRequest.developer());
        game.setDeveloper(developer);

        Publisher publisher = publisherService.findPublisherById(gameRequest.publisher());
        game.setPublisher(publisher);

        game.setTags(findEntitiesByIds(gameRequest.tags(), tagRepository, "Tag"));
        game.setGenres(findEntitiesByIds(gameRequest.genres(), genreRepository, "Genre"));
        game.setPlatforms(findEntitiesByIds(gameRequest.platforms(), platformRepository, "Platform"));

        // Object GameMedia from DTO
        gameMedia.setBannerUrl(gameRequest.bannerImageUrl());
        gameMedia.setTrailer(gameRequest.trailerUrl());
        gameMedia.setScreenshotUrl(gameRequest.screenshotUrl());
        gameMedia.setTrailerScreenshot(gameRequest.trailerScreenshotUrl());
        gameMedia.setGames(game);
        // Save to media db
        gameMedia = gameMediaRepository.save(gameMedia);
        //Create relationship
        game.setGameMedia(gameMedia);
        return game;
    }

    @Override
    public Game getInvisibleGameById(Long gameId) {
        return gameService.getGameById(gameId);
    }

    @Override
    public List<GameModelWithGenreLimit> searchGamesByTitleWithoutActiveCheck(String text, int qty) {
        return gameRepository.findSimilarTitlesWithoutActiveCheck(gameService.transformWord(text)).stream()
                .map(game -> gameMapper.toLimitGenreGame(game, false))
                .limit(qty)
                .toList();
    }
    @Override
    public AdminResponse deleteGame(Long gameId) {
        Game game = gameService.getGameById(gameId);
        gameRepository.delete(game);
        return AdminResponse.builder()
                .response("Game with id " + game.getId() + " was deleted!")
                .build();
    }

    @Override
    public GeneralGame getExtendedGamesByPageForAdmin(GameCriteria gameCriteria) {
        return gameService.getExtendedGamesByPage(gameCriteria, false);
    }

    @Override
    public AdminResponse toggleGameActiveStatus(Long gameId, boolean active) {
        Game game = gameRepository.findById(gameId).orElseThrow(() ->
                new GlobalServiceException(HttpStatus.NOT_FOUND, "Game with id " + gameId + " is not found."));
        if (active != Boolean.TRUE.equals(game.getActive())) {
            game.setActive(active);
            String status = active ? "activated" : "deactivated";
            gameRepository.save(game);
            return AdminResponse.builder().response("Game with id " + gameId + " is " + status + "!").build();
        } else {
            String status = active ? "already activated" : "already deactivated";
            return AdminResponse.builder().response("Game with id " + gameId + " is " + status + "!").build();
        }
    }

    private <T> Set<T> findEntitiesByIds(Set<Long> ids, JpaRepository<T, Long> repository, String entityName) {
        return ids.stream()
                .map(id -> repository.findById(id)
                        .orElseThrow(() -> new GlobalServiceException(HttpStatus.NOT_FOUND,
                                entityName + " with id " + id + " not found")))
                .collect(Collectors.toSet());
    }
}
