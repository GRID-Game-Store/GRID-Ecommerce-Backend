package com.khomsi.backend.main.admin.controller;

import com.khomsi.backend.main.admin.model.request.EntityModelRequest;
import com.khomsi.backend.main.admin.model.response.AdminModelResponse;
import com.khomsi.backend.main.admin.model.response.AdminResponse;
import com.khomsi.backend.main.admin.service.AdminUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static com.khomsi.backend.сonfig.ApplicationConfig.BEARER_KEY_SECURITY_SCHEME;

@RestController
@Tag(name = "Admin-User", description = "CRUD operation for Admin-User Controller")
@RequestMapping("/api/v1/admin/users")
@Validated
@RequiredArgsConstructor
public class AdminUserController {
    private final AdminUserService adminUserService;

    @GetMapping
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)}, summary = "Get all users")
    @ResponseStatus(HttpStatus.OK)
    public AdminModelResponse showAllUserByPage(@Valid EntityModelRequest entityModelRequest) {
        return adminUserService.getAllUsers(entityModelRequest);
    }

    @PostMapping("/update/balance/{user-id}")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)}, summary = "Update user's balance")
    @ResponseStatus(HttpStatus.OK)
    public AdminResponse changeUserBalance(@PathVariable("user-id") @NotEmpty String userId,
                                           @RequestParam("newBalance") @NotNull BigDecimal newBalance) {
        return adminUserService.updateUserBalance(userId, newBalance);
    }
}
