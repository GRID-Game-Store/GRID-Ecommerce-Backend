package com.khomsi.backend.additional.developer.controller;

import com.khomsi.backend.additional.developer.model.entity.Developer;
import com.khomsi.backend.additional.developer.service.DeveloperService;
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
@Tag(name = "Developer", description = "CRUD operation for Developer Controller")
@RequestMapping("/api/v1/developers")
@Validated
@RequiredArgsConstructor
public class DeveloperController {
    private final DeveloperService developerService;

    @GetMapping
    @Operation(summary = "Get all developers")
    @ResponseStatus(HttpStatus.OK)
    public List<Developer> showAllDevelopers() {
        return developerService.getAllDevelopers();
    }
}
