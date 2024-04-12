package com.khomsi.backend.main.admin.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GameInsertDTO(String gameTitle,
                            String gameDescription,
                            LocalDate gameReleaseDate,
                            String gameSystemRequirements,
                            BigDecimal gamePrice,
                            BigDecimal gameDiscount,
                            String gamePermittedAge,
                            String gameCoverImageUrl,
                            String gameBannerImageUrl,
                            String gameTrailerUrl,
                            String gameScreenshotUrl,
                            String gameTrailerScreenshotUrl,
                            String developerName,
                            String publisherName,
                            String tags,
                            String genres,
                            String platforms) {
}