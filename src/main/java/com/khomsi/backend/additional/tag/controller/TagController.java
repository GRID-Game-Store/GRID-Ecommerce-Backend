package com.khomsi.backend.additional.tag.controller;

import com.khomsi.backend.additional.tag.model.entity.Tag;
import com.khomsi.backend.additional.tag.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tags")
@io.swagger.v3.oas.annotations.tags.Tag(name = "Tag", description = "CRUD operation for Tag Controller")
@Validated
@RequiredArgsConstructor
public class TagController {
    private final TagService publisherService;

    @GetMapping
    @Operation(summary = "Get all tags")
    @ResponseStatus(HttpStatus.OK)
    public List<Tag> showAllTags() {
        return publisherService.getAllTags();
    }
}
