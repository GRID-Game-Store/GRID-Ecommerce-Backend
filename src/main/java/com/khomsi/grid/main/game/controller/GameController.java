package com.khomsi.grid.main.game.controller;

import com.khomsi.grid.main.game.model.dto.GeneralGame;
import com.khomsi.grid.main.game.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Game", description = "CRUD operation for Game Controller")
@RequestMapping("api/v1/games")
public record GameController(GameService gameService) {
    @GetMapping
    @Operation(summary = "Get all games")
    @ResponseStatus(HttpStatus.OK)
    public GeneralGame showAllGamesByPage(@RequestParam(defaultValue = "1") int page,
                                          @RequestParam(defaultValue = "1") int pageSize) {
        return gameService.showGamesByPage(page, pageSize);
    }
}
