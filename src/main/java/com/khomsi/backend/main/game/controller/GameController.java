package com.khomsi.backend.main.game.controller;

import com.khomsi.backend.main.game.model.dto.GameCriteria;
import com.khomsi.backend.main.game.model.dto.GameModelWithGenreLimit;
import com.khomsi.backend.main.game.model.dto.GeneralGame;
import com.khomsi.backend.main.game.model.dto.PopularGameModel;
import com.khomsi.backend.main.game.model.entity.Game;
import com.khomsi.backend.main.game.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.khomsi.backend.сonfig.ApplicationConfig.BEARER_KEY_SECURITY_SCHEME;

@RestController
@Tag(name = "Game", description = "CRUD operation for Game Controller")
@RequestMapping("/api/v1/games")
@Validated
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;

    @GetMapping
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)}, summary = "Get all games")
    @ResponseStatus(HttpStatus.OK)
    public GeneralGame showAllGamesByPage(@Valid GameCriteria gameCriteria) {
        return gameService.getExtendedGamesByPage(gameCriteria);
    }

    @GetMapping("/genre")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)}, summary = "Get games by genre")
    @ResponseStatus(HttpStatus.OK)
    public List<GameModelWithGenreLimit> showGamesByGenre(
            @RequestParam(value = "genre") String genre,
            @RequestParam(value = "qty", defaultValue = "5")
            @Min(1) @Max(Integer.MAX_VALUE) int gameQuantity) {
        return gameService.getGamesByGenre(gameQuantity, genre);
    }

    @GetMapping("/offers")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)}, summary = "Get games by 'special' offer")
    @ResponseStatus(HttpStatus.OK)
    public List<PopularGameModel> showGamesBySpecialOffer(
            @RequestParam(value = "query") String query,
            @RequestParam(value = "qty", defaultValue = "5")
            @Min(1) @Max(Integer.MAX_VALUE) int gameQuantity) {
        return gameService.getSpecialOffers(query.toLowerCase(), gameQuantity);
    }

    @GetMapping("/popular")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)}, summary = "Get most 'popular' games")
    @ResponseStatus(HttpStatus.OK)
    public List<PopularGameModel> showPopularQtyOfGames(
            @RequestParam(value = "qty", defaultValue = "5")
            @Min(1) @Max(Integer.MAX_VALUE) int gameQuantity) {
        return gameService.getPopularQtyOfGames(gameQuantity);
    }

    @GetMapping("/random")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)}, summary = "Get n-number of random games")
    @ResponseStatus(HttpStatus.OK)
    public List<GameModelWithGenreLimit> showRandomQtyOfGames(
            @RequestParam(value = "qty", defaultValue = "20")
            @Min(1) @Max(Integer.MAX_VALUE) int gameQuantity) {
        return gameService.getRandomQtyOfGames(gameQuantity);
    }

    @GetMapping("/search")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)}, summary = "Get game by title (symbol by symbol)")
    @ResponseStatus(HttpStatus.OK)
    public List<GameModelWithGenreLimit> showSearchedGame(
            @RequestParam(value = "title") String title,
            @RequestParam(value = "qty", defaultValue = "20")
            @Min(1) @Max(Integer.MAX_VALUE) int gameQuantity) {
        return gameService.searchGamesByTitle(title, gameQuantity);
    }

    @GetMapping("/{game-id}")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)}, summary = "Get game by id")
    @ResponseStatus(HttpStatus.OK)
    public Game showGameById(
            @PathVariable("game-id")
            @Min(1) @Max(Long.MAX_VALUE) Long gameId) {
        return gameService.getGameById(gameId);
    }
}