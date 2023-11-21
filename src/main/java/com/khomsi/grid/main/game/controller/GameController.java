package com.khomsi.grid.main.game.controller;

import com.khomsi.grid.main.game.model.dto.GameModelWithGenreLimit;
import com.khomsi.grid.main.game.model.dto.GeneralGame;
import com.khomsi.grid.main.game.model.dto.PopularGameModel;
import com.khomsi.grid.main.game.model.entity.Game;
import com.khomsi.grid.main.game.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Game", description = "CRUD operation for Game Controller")
@RequestMapping("/api/v1/games")
@Validated
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;

    @GetMapping
    @Operation(summary = "Get all games")
    @ResponseStatus(HttpStatus.OK)
    public GeneralGame showAllGamesByPage(
            @RequestParam(value = "page", defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(value = "size", defaultValue = "5") @Min(1) @Max(Integer.MAX_VALUE) int pageSize,
            @RequestParam(required = false, value = "title") String title,
            @RequestParam(defaultValue = "id,desc") String[] sort) {
        return gameService.getGamesByPage(page, pageSize, sort, title);
    }

    @GetMapping("/genre")
    @Operation(summary = "Get games by genre")
    @ResponseStatus(HttpStatus.OK)
    public List<GameModelWithGenreLimit> showGamesByGenre(
            @RequestParam(value = "genre") String genre,
            @RequestParam(value = "qty", defaultValue = "5")
            @Min(1) @Max(Integer.MAX_VALUE) int gameQuantity) {
        return gameService.getGamesByGenre(gameQuantity, genre);
    }

    @GetMapping("/offers")
    @Operation(summary = "Get games by 'special' offer")
    @ResponseStatus(HttpStatus.OK)
    public List<GameModelWithGenreLimit> showGamesBySpecialOffer(
            @RequestParam(value = "query") String query,
            @RequestParam(value = "qty", defaultValue = "5")
            @Min(1) @Max(Integer.MAX_VALUE) int gameQuantity) {
        return gameService.getSpecialOffers(query.toLowerCase(), gameQuantity);
    }

    @GetMapping("/popular")
    @Operation(summary = "Get most 'popular' games")
    @ResponseStatus(HttpStatus.OK)
    public List<PopularGameModel> showPopularQtyOfGames(
            @RequestParam(value = "qty", defaultValue = "5")
            @Min(1) @Max(Integer.MAX_VALUE) int gameQuantity) {
        return gameService.getPopularQtyOfGames(gameQuantity);
    }

    @GetMapping("/random")
    @Operation(summary = "Get n-number of random games")
    @ResponseStatus(HttpStatus.OK)
    public List<GameModelWithGenreLimit> showRandomQtyOfGames(
            @RequestParam(value = "qty", defaultValue = "20")
            @Min(1) @Max(Integer.MAX_VALUE) int gameQuantity) {
        return gameService.getRandomQtyOfGames(gameQuantity);
    }

    @GetMapping("/search")
    @Operation(summary = "Get game by title (symbol by symbol)")
    @ResponseStatus(HttpStatus.OK)
    public List<GameModelWithGenreLimit> showSearchedGame(
            @RequestParam(value = "title") String title,
            @RequestParam(value = "qty", defaultValue = "20")
            @Min(1) @Max(Integer.MAX_VALUE) int gameQuantity) {
        return gameService.searchGamesByTitle(title, gameQuantity);
    }

    @GetMapping("/{game-id}")
    @Operation(summary = "Get game by id")
    @ResponseStatus(HttpStatus.OK)
    public Game showGameById(
            @PathVariable("game-id")
            @Min(1) @Max(Long.MAX_VALUE) Long gameId) {
        return gameService.getGameById(gameId);
    }
}