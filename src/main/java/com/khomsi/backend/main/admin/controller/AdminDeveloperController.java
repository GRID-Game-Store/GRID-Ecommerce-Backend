package com.khomsi.backend.main.admin.controller;

import com.khomsi.backend.main.admin.model.request.EntityEditRequest;
import com.khomsi.backend.main.admin.model.response.AdminResponse;
import com.khomsi.backend.main.admin.service.AdminDeveloperService;
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
@Tag(name = "Admin-Developer", description = "CRUD operation for Admin-Developer Controller")
@RequestMapping("/api/v1/admin/developers")
@Validated
@RequiredArgsConstructor
public class AdminDeveloperController {
    private final AdminDeveloperService developerService;

    @PostMapping("/edit")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Edit developer")
    @ResponseStatus(HttpStatus.OK)
    public AdminResponse editDeveloper(@Valid @RequestBody EntityEditRequest entityEditRequest) {
        return developerService.editDeveloper(entityEditRequest);
    }

    @DeleteMapping("/{developer-id}")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Delete developer")
    @ResponseStatus(HttpStatus.OK)
    public AdminResponse deleteDeveloper(@PathVariable("developer-id")
                                         @Min(1) @Max(Long.MAX_VALUE) Long developerId) {
        return developerService.deleteDeveloper(developerId);
    }
}
