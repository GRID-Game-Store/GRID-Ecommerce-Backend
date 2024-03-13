package com.khomsi.backend.main.user.controller;

import com.khomsi.backend.main.user.model.dto.BalanceUserInfoDTO;
import com.khomsi.backend.main.user.model.dto.FullUserInfoDTO;
import com.khomsi.backend.main.user.model.dto.UserShortGamesDTO;
import com.khomsi.backend.main.user.service.UserGamesService;
import com.khomsi.backend.main.user.service.UserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.khomsi.backend.сonfig.ApplicationConfig.BEARER_KEY_SECURITY_SCHEME;

@RestController
@RequestMapping("/api/v1/users")
@Validated
@RequiredArgsConstructor
@Tag(name = "User", description = "CRUD operation for User Controller")
public class UserController {
    private final UserInfoService userInfoService;
    private final UserGamesService userGamesService;

    @GetMapping("/profile")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Get user's information")
    @ResponseStatus(HttpStatus.OK)
    public FullUserInfoDTO showUserInformation() {
        return userInfoService.getCurrentUser();
    }

    @GetMapping("/balance")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Get user's balance")
    @ResponseStatus(HttpStatus.OK)
    public BalanceUserInfoDTO showUserBalance() {
        return userInfoService.getUserBalance();
    }

    @GetMapping("/games")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Get user's games")
    @ResponseStatus(HttpStatus.OK)
    public List<UserShortGamesDTO> showUserGames() {
        return userGamesService.getAllUserGames();
    }
}