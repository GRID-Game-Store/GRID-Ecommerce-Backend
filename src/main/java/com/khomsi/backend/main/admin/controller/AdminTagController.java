package com.khomsi.backend.main.admin.controller;

import com.khomsi.backend.main.admin.model.request.EntityEditRequest;
import com.khomsi.backend.main.admin.model.request.EntityInsertRequest;
import com.khomsi.backend.main.admin.model.response.AdminResponse;
import com.khomsi.backend.main.admin.service.AdminTagService;
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
@Tag(name = "Admin-Tag", description = "CRUD operation for Admin-Tag Controller")
@RequestMapping("/api/v1/admin/tags")
@Validated
@RequiredArgsConstructor
public class AdminTagController {
    private final AdminTagService adminTagService;

    @PostMapping("/add")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Add tag to db")
    @ResponseStatus(HttpStatus.CREATED)
    public AdminResponse addTag(@Valid @RequestBody EntityInsertRequest entityInsertRequest) {
        return adminTagService.addTag(entityInsertRequest);
    }

    @PostMapping("/edit")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Edit tag")
    @ResponseStatus(HttpStatus.OK)
    public AdminResponse editTag(@Valid @RequestBody EntityEditRequest entityEditRequest) {
        return adminTagService.editTag(entityEditRequest);
    }

    @DeleteMapping("/{tag-id}")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Delete tag")
    @ResponseStatus(HttpStatus.OK)
    public AdminResponse deleteTag(@PathVariable("tag-id")
                                   @Min(1) @Max(Long.MAX_VALUE) Long tagId) {
        return adminTagService.deleteTag(tagId);
    }
}
