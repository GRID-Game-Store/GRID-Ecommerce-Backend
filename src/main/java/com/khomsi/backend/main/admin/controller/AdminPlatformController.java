package com.khomsi.backend.main.admin.controller;

import com.khomsi.backend.main.admin.model.request.EntityEditRequest;
import com.khomsi.backend.main.admin.model.request.EntityInsertRequest;
import com.khomsi.backend.main.admin.model.response.AdminResponse;
import com.khomsi.backend.main.admin.service.AdminPlatformService;
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
@Tag(name = "Admin-Platform", description = "CRUD operation for Admin-Platform Controller")
@RequestMapping("/api/v1/admin/platforms")
@Validated
@RequiredArgsConstructor
public class AdminPlatformController {
    private final AdminPlatformService adminPlatformService;

    @PostMapping("/add")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Add platform to db")
    @ResponseStatus(HttpStatus.CREATED)
    public AdminResponse addPlatform(@Valid @RequestBody EntityInsertRequest entityInsertRequest) {
        return adminPlatformService.addPlatform(entityInsertRequest);
    }

    @PostMapping("/edit")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Edit platform")
    @ResponseStatus(HttpStatus.OK)
    public AdminResponse editPlatform(@Valid @RequestBody EntityEditRequest entityEditRequest) {
        return adminPlatformService.editPlatform(entityEditRequest);
    }

    @DeleteMapping("/delete/{platform-id}")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Delete platform")
    @ResponseStatus(HttpStatus.OK)
    public AdminResponse deletePlatform(@PathVariable("platform-id")
                                   @Min(1) @Max(Long.MAX_VALUE) Long id) {
        return adminPlatformService.deletePlatform(id);
    }
}
