package com.khomsi.backend.additional.genre.controller;

import com.khomsi.backend.additional.genre.model.entity.Genre;
import com.khomsi.backend.additional.genre.service.GenreService;
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
@Tag(name = "Genre", description = "CRUD operation for Genre Controller")
@RequestMapping("/api/v1/genres")
@Validated
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping
    @Operation(summary = "Get all genres")
    @ResponseStatus(HttpStatus.OK)
    public List<Genre> showAllGenres() {
        return genreService.getAllGenres();
    }
}