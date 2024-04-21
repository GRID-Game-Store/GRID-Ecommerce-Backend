package com.khomsi.backend.main.admin.controller;

import com.khomsi.backend.main.admin.model.request.EntityModelRequest;
import com.khomsi.backend.main.admin.model.request.ReviewRequest;
import com.khomsi.backend.main.admin.model.response.AdminModelResponse;
import com.khomsi.backend.main.admin.model.response.AdminResponse;
import com.khomsi.backend.main.admin.service.AdminReviewService;
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
@Tag(name = "Admin-Review", description = "CRUD operation for Admin-Review Controller")
@RequestMapping("/api/v1/admin/reviews")
@Validated
@RequiredArgsConstructor
public class AdminReviewController {
    private final AdminReviewService adminReviewService;

    @GetMapping("/{game-id}")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)}, summary = "Get all reviews for game")
    @ResponseStatus(HttpStatus.OK)
    public AdminModelResponse showAllReviewsByGame(@PathVariable("game-id")
                                                   @Min(1) @Max(Long.MAX_VALUE) Long gameId,
                                                   @Valid EntityModelRequest entityModelRequest) {
        return adminReviewService.getAllReviewsByGame(gameId, entityModelRequest);
    }

    @PostMapping("/edit")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Edit review on game")
    @ResponseStatus(HttpStatus.OK)
    public AdminResponse editReviewOnGame(@Valid @RequestBody ReviewRequest reviewRequest) {
        return adminReviewService.editReview(reviewRequest);
    }

    @DeleteMapping("/{review-id}")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Delete review from game")
    @ResponseStatus(HttpStatus.OK)
    public AdminResponse deleteReviewFromGame(@PathVariable("review-id")
                                              @Min(1) @Max(Long.MAX_VALUE) Long reviewId) {
        return adminReviewService.deleteReview(reviewId);
    }
}
