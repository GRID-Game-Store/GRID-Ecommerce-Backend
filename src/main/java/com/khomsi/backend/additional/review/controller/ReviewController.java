package com.khomsi.backend.additional.review.controller;

import com.khomsi.backend.additional.review.model.dto.ReviewDTO;
import com.khomsi.backend.additional.review.model.dto.ReviewRequest;
import com.khomsi.backend.additional.review.model.dto.ReviewResponse;
import com.khomsi.backend.additional.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.khomsi.backend.сonfig.ApplicationConfig.BEARER_KEY_SECURITY_SCHEME;

@RestController
@Tag(name = "Review", description = "CRUD operation for Review Controller")
@RequestMapping("/api/v1/reviews")
@Validated
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/get/{game-id}")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Get my review for game")
    public ResponseEntity<ReviewDTO> getReviewForGame(@PathVariable("game-id")
                                                          @Min(1) @Max(Long.MAX_VALUE) Long gameId) {
        return reviewService.getReviewForGameByUser(gameId);
    }
    @PostMapping("/add/{game-id}")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Add review to game")
    public ResponseEntity<ReviewResponse> addReviewToGame(@PathVariable("game-id")
                                                          @Min(1) @Max(Long.MAX_VALUE) Long gameId,
                                                          @RequestBody @Valid ReviewRequest reviewRequest) {
        return reviewService.addReview(gameId, reviewRequest);
    }

    @PutMapping("/edit/{review-id}")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Edit review to game")
    public ResponseEntity<ReviewResponse> editReviewToGame(@PathVariable("review-id")
                                                           @Min(1) @Max(Long.MAX_VALUE) Long reviewId,
                                                           @RequestBody @Valid ReviewRequest reviewRequest) {
        return reviewService.editReview(reviewId, reviewRequest);
    }

    @DeleteMapping("/delete/{review-id}")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Delete review from game")
    public ResponseEntity<ReviewResponse> deleteReviewFromGame(@PathVariable("review-id")
                                                               @Min(1) @Max(Long.MAX_VALUE) Long reviewId) {
        return reviewService.deleteReview(reviewId);
    }

    @GetMapping("/{game-id}")
    @Operation(summary = "View all reviews to game")
    public List<ReviewDTO> viewReviewForGame(@PathVariable("game-id")
                                             @Min(1) @Max(Long.MAX_VALUE) Long gameId) {
        return reviewService.viewReview(gameId);
    }
}
