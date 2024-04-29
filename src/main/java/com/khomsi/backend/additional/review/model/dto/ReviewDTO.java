package com.khomsi.backend.additional.review.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ReviewDTO(Long reviewId,
                        String username,
                        Integer rating, String comment,
                        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm dd-MM-yyyy")
                        LocalDateTime reviewDate) {
}
