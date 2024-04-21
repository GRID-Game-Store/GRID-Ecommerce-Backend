package com.khomsi.backend.additional.review.mapper.impl;

import com.khomsi.backend.additional.review.mapper.ReviewMapper;
import com.khomsi.backend.additional.review.model.dto.ReviewDTO;
import com.khomsi.backend.additional.review.model.entity.Review;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ReviewMapperImpl implements ReviewMapper {
    @Override
    public ReviewDTO toReviewToDTO(Review review) {
        String username = review.getUsers().getUsername();
        return new ReviewDTO(
                review.getId(),
                username,
                review.getRating(),
                review.getComment(),
                review.getReviewDate()
        );
    }
}
