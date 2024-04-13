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
import com.khomsi.backend.main.admin.model.dto.GameInsertDTO;
import com.khomsi.backend.main.admin.model.response.AdminResponse;
import com.khomsi.backend.main.admin.service.AdminService;
import com.khomsi.backend.main.game.GameRepository;
import com.khomsi.backend.main.game.model.entity.Game;
import com.khomsi.backend.main.game.service.GameService;
import com.khomsi.backend.main.handler.exception.GlobalServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final DeveloperService developerService;
    private final PublisherService publisherService;
    private final TagRepository tagRepository;
    private final GenreRepository genreRepository;
    private final PlatformRepository platformRepository;
    private final GameService gameService;
    private final GameRepository gameRepository;
    private final GameMediaRepository gameMediaRepository;

    @Override
    @Transactional
    public AdminResponse addGameToDb(GameInsertDTO gameDTO) {
        // Check if the game with the same title already exists
        if (gameRepository.existsGameByTitleIgnoreCase(gameDTO.title())) {
            throw new GlobalServiceException(HttpStatus.BAD_REQUEST,
                    "Game with title '" + gameDTO.title() + "' already exists");
        }
        Game game = new Game();
        game.setTitle(gameDTO.title());
        game.setDescription(gameDTO.description());
        game.setReleaseDate(gameDTO.releaseDate());
        game.setSystemRequirements(gameDTO.systemRequirements());
        game.setAboutGame(gameDTO.aboutGame());
        game.setPrice(gameDTO.price());
        game.setActive(gameDTO.active());
        game.setDiscount(gameDTO.discount());
        game.setPermitAge(gameDTO.permitAge().getValue());
        game.setCoverImageUrl(gameDTO.coverImageUrl());

        // Set developer and publisher
        Developer developer = developerService.findDeveloperById(gameDTO.developer());
        game.setDeveloper(developer);

        Publisher publisher = publisherService.findPublisherById(gameDTO.publisher());
        game.setPublisher(publisher);

        game.setTags(findEntitiesByIds(gameDTO.tags(), tagRepository, "Tag"));
        game.setGenres(findEntitiesByIds(gameDTO.genres(), genreRepository, "Genre"));
        game.setPlatforms(findEntitiesByIds(gameDTO.platforms(), platformRepository, "Platform"));

        // Create object GameMedia from DTO
        GameMedia gameMedia = new GameMedia();
        gameMedia.setBannerUrl(gameDTO.bannerImageUrl());
        gameMedia.setTrailer(gameDTO.trailerUrl());
        gameMedia.setScreenshotUrl(gameDTO.screenshotUrl());
        gameMedia.setTrailerScreenshot(gameDTO.trailerScreenshotUrl());
        gameMedia.setGames(game);
        // Save to media db
        gameMedia = gameMediaRepository.save(gameMedia);
        //Create relationship
        game.setGameMedia(gameMedia);
        // Save game in repository
        game = gameRepository.save(game);

        return AdminResponse.builder()
                .response("Game with id " + game.getId() + " was added!")
                .build();
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
