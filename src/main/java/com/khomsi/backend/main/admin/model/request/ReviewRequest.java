package com.khomsi.backend.main.admin.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record ReviewRequest(
        @NotNull
        @Size(max = 2000, message = "Comment length must be less than or equal to 2000 characters")
        @NotEmpty(message = "Comment must not be empty")
        String comment,
        @NotNull
        @Min(1)
        Long reviewId) {
}
