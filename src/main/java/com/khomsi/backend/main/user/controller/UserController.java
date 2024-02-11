package com.khomsi.backend.main.user.controller;

import com.khomsi.backend.main.user.model.dto.FullUserInfoDTO;
import com.khomsi.backend.main.user.service.UserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@Validated
@RequiredArgsConstructor
@Tag(name = "User", description = "CRUD operation for User Controller")
public class UserController {
    private final UserInfoService userInfoService;

    @GetMapping("/profile")
    @Operation(summary = "Get user's information")
    @ResponseStatus(HttpStatus.OK)
    public FullUserInfoDTO showUserInformation() {
        return userInfoService.getCurrentUser();
    }

    //TODO endpoint for tests, will be removed in future
    @GetMapping("/test")
    @Operation(summary = "Controller to test the authorization of user")
    @ResponseStatus(HttpStatus.OK)
    public String showTest() {
        return "Test";
    }
}