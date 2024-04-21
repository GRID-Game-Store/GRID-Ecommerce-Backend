package com.khomsi.backend.additional.review.mapper;

import com.khomsi.backend.additional.review.model.dto.ReviewDTO;
import com.khomsi.backend.additional.review.model.entity.Review;

public interface ReviewMapper {
    ReviewDTO toReviewToDTO(Review review);
}
