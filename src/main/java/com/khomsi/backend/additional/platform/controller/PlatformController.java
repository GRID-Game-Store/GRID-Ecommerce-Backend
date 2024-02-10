package com.khomsi.backend.additional.platform.controller;

import com.khomsi.backend.additional.platform.model.entity.Platform;
import com.khomsi.backend.additional.platform.service.PlatformService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Platform", description = "CRUD operation for Platform Controller")
@RequestMapping("/api/v1/platforms")
@Validated
@RequiredArgsConstructor
public class PlatformController {
    private final PlatformService platformService;

    @GetMapping
    @Operation(summary = "Get all platforms")
    @ResponseStatus(HttpStatus.OK)
    public List<Platform> showAllPlatforms() {
        return platformService.getAllPlatforms();
    }
}
