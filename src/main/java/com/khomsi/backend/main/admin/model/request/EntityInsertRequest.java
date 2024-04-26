package com.khomsi.backend.main.admin.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record EntityInsertRequest(
        @NotNull
        @Size(max = 255, message = "Name length must be less than or equal to 255 characters")
        @NotEmpty(message = "Name must not be empty")
        String name) {
}