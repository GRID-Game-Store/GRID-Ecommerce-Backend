package com.khomsi.backend.main.admin.controller;

import com.khomsi.backend.main.admin.model.request.EntityEditRequest;
import com.khomsi.backend.main.admin.model.request.EntityInsertRequest;
import com.khomsi.backend.main.admin.model.response.AdminResponse;
import com.khomsi.backend.main.admin.service.AdminGenreService;
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
@Tag(name = "Admin-Genre", description = "CRUD operation for Admin-Genre Controller")
@RequestMapping("/api/v1/admin/genres")
@Validated
@RequiredArgsConstructor
public class AdminGenreController {
    private final AdminGenreService adminGenreService;

    @PostMapping("/add")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Add genre to db")
    @ResponseStatus(HttpStatus.CREATED)
    public AdminResponse addGenre(@Valid @RequestBody EntityInsertRequest entityInsertRequest) {
        return adminGenreService.addGenre(entityInsertRequest);
    }

    @PostMapping("/edit")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Edit genre")
    @ResponseStatus(HttpStatus.OK)
    public AdminResponse editGenre(@Valid @RequestBody EntityEditRequest entityEditRequest) {
        return adminGenreService.editGenre(entityEditRequest);
    }

    @DeleteMapping("/delete/{genre-id}")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Delete genre")
    @ResponseStatus(HttpStatus.OK)
    public AdminResponse deleteGenre(@PathVariable("genre-id")
                                     @Min(1) @Max(Long.MAX_VALUE) Long id) {
        return adminGenreService.deleteGenre(id);
    }
}
