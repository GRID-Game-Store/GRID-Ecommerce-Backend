package com.khomsi.backend.main.admin.controller;

import com.khomsi.backend.main.admin.model.dto.GameDTO;
import com.khomsi.backend.main.admin.model.response.AdminResponse;
import com.khomsi.backend.main.admin.service.AdminService;
import com.khomsi.backend.main.game.model.dto.GameCriteria;
import com.khomsi.backend.main.game.model.dto.GeneralGame;
import com.khomsi.backend.main.game.model.entity.Game;
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

import static com.khomsi.backend.сonfig.ApplicationConfig.BEARER_KEY_SECURITY_SCHEME;

@RestController
@Tag(name = "Admin-Game", description = "CRUD operation for Admin-Game Controller")
@RequestMapping("/api/v1/admin/games")
@Validated
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)}, summary = "Get all games for Admin")
    @ResponseStatus(HttpStatus.OK)
    public GeneralGame showAllGamesByPage(@Valid GameCriteria gameCriteria) {
        return adminService.getExtendedGamesByPageForAdmin(gameCriteria);
    }

    @GetMapping("/{game-id}")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)}, summary = "Get game by id")
    @ResponseStatus(HttpStatus.OK)
    public Game showGameById(
            @PathVariable("game-id")
            @Min(1) @Max(Long.MAX_VALUE) Long gameId) {
        return adminService.getInvisibleGameById(gameId);
    }

    @PostMapping("/add")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)}, summary = "Add game to db")
    @ResponseStatus(HttpStatus.CREATED)
    public AdminResponse addGameToDb(@RequestBody GameDTO gameDTO) {
        return adminService.addGameToDb(gameDTO);
    }

    @PutMapping("/edit/{game-id}")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)}, summary = "Edit game to db")
    @ResponseStatus(HttpStatus.CREATED)
    public AdminResponse editGameToDb(@PathVariable("game-id") Long gameId, @RequestBody GameDTO gameDTO) {
        return adminService.editGame(gameId, gameDTO);
    }

    @PostMapping("/activate/{game-id}")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Change game visibility to activate/deactivate in db")
    @ResponseStatus(HttpStatus.OK)
    public AdminResponse changeGameVisibility(@PathVariable("game-id")
                                              @Min(1) @Max(Long.MAX_VALUE) Long gameId,
                                              @RequestParam(value = "activate") boolean activate) {
        return adminService.toggleGameActiveStatus(gameId, activate);
    }
}
