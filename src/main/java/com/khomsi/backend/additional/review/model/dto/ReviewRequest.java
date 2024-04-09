package com.khomsi.backend.additional.review.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record ReviewRequest(
        @Size(max = 2000, message = "Comment length must be less than or equal to 2000 characters")
        @NotEmpty(message = "Comment must not be empty")
        String comment,
        @Min(value = 1, message = "Rating must be at least 1")
        @Max(value = 5, message = "Rating must be at most 5")
        Integer rating) {
}
