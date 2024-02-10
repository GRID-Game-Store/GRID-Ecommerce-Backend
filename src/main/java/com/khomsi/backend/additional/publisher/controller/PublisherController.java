package com.khomsi.backend.additional.publisher.controller;

import com.khomsi.backend.additional.publisher.model.entity.Publisher;
import com.khomsi.backend.additional.publisher.service.PublisherService;
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
@Tag(name = "Publisher", description = "CRUD operation for Publisher Controller")
@RequestMapping("/api/v1/publishers")
@Validated
@RequiredArgsConstructor
public class PublisherController {
    private final PublisherService publisherService;

    @GetMapping
    @Operation(summary = "Get all publishers")
    @ResponseStatus(HttpStatus.OK)
    public List<Publisher> showAllPublishers() {
        return publisherService.getAllPublishers();
    }
}
