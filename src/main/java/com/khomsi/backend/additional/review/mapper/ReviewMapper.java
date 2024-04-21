package com.khomsi.backend.additional.review.mapper;

import com.khomsi.backend.additional.review.model.dto.ReviewDTO;
import com.khomsi.backend.additional.review.model.entity.Review;
import com.khomsi.backend.main.checkout.model.dto.TransactionDTO;
import com.khomsi.backend.main.checkout.model.entity.Transaction;

public interface ReviewMapper {
    ReviewDTO toReviewToDTO(Review review);
}
