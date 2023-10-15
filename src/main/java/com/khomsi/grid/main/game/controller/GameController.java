package com.khomsi.grid.main.game.controller;

import com.khomsi.grid.main.game.model.dto.GeneralGame;
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
        return gameService.showGamesByPage(page, pageSize, sort, title);
    }
}