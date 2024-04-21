package com.khomsi.backend.main.admin.controller;

import com.khomsi.backend.main.admin.model.request.GameRequest;
import com.khomsi.backend.main.admin.model.response.AdminResponse;
import com.khomsi.backend.main.admin.service.AdminGameService;
import com.khomsi.backend.main.game.model.dto.GameCriteria;
import com.khomsi.backend.main.game.model.dto.GameModelWithGenreLimit;
import com.khomsi.backend.main.game.model.dto.GeneralGame;
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
@Tag(name = "Admin-Game", description = "CRUD operation for Admin-Game Controller")
@RequestMapping("/api/v1/admin/games")
@Validated
@RequiredArgsConstructor
public class AdminGameController {
    private final AdminGameService adminGameService;

    @GetMapping
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)}, summary = "Get all games")
    @ResponseStatus(HttpStatus.OK)
    public GeneralGame showAllGamesByPage(@Valid GameCriteria gameCriteria) {
        return adminGameService.getExtendedGamesByPageForAdmin(gameCriteria);
    }

    @GetMapping("/search")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)}, summary = "Get game by title")
    @ResponseStatus(HttpStatus.OK)
    public List<GameModelWithGenreLimit> showGameByTitle(
            @RequestParam(value = "title") String title,
            @RequestParam(value = "qty", defaultValue = "20")
            @Min(1) @Max(Integer.MAX_VALUE) int gameQuantity) {
        return adminGameService.searchGamesByTitleWithoutActiveCheck(title, gameQuantity);
    }

    @PostMapping("/add")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)}, summary = "Add game to db")
    @ResponseStatus(HttpStatus.CREATED)
    public AdminResponse addGameToDb(@RequestBody GameRequest gameRequest) {
        return adminGameService.addGameToDb(gameRequest);
    }

    @PutMapping("/edit/{game-id}")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)}, summary = "Edit game to db")
    @ResponseStatus(HttpStatus.CREATED)
    public AdminResponse editGameToDb(@PathVariable("game-id") Long gameId, @RequestBody GameRequest gameRequest) {
        return adminGameService.editGame(gameId, gameRequest);
    }

    @PostMapping("/activate/{game-id}")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Change game visibility to activate/deactivate in db")
    @ResponseStatus(HttpStatus.OK)
    public AdminResponse changeGameVisibility(@PathVariable("game-id")
                                              @Min(1) @Max(Long.MAX_VALUE) Long gameId,
                                              @RequestParam(value = "activate") boolean activate) {
        return adminGameService.toggleGameActiveStatus(gameId, activate);
    }
}
