package com.khomsi.backend.main.user.controller;

import com.khomsi.backend.main.user.model.dto.FullUserInfoDTO;
import com.khomsi.backend.main.user.service.UserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@Validated
@RequiredArgsConstructor
public class UserController {
    private final UserInfoService userInfoService;

    @GetMapping("/profile")
    @Operation(summary = "Get user")
    @ResponseStatus(HttpStatus.OK)
    public FullUserInfoDTO showAllGamesByPage() {
        return userInfoService.getCurrentUser();
    }
    //TODO endpoint for tests, will be removed in future
    @GetMapping("/test")
    @Operation(summary = "Get test")
    @ResponseStatus(HttpStatus.OK)
    public String showTest() {
        return "Test";
    }
}