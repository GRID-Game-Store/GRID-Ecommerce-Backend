package com.khomsi.backend.additional.review.service;

import com.khomsi.backend.additional.review.model.dto.ReviewDTO;
import com.khomsi.backend.additional.review.model.dto.ReviewRequest;
import com.khomsi.backend.additional.review.model.dto.ReviewResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReviewService {
    ResponseEntity<ReviewResponse> addReview(Long gameId, ReviewRequest reviewRequest);

    ResponseEntity<ReviewResponse> editReview(Long reviewId, ReviewRequest reviewRequest);

    List<ReviewDTO> viewReview(Long gameId);

    ResponseEntity<ReviewResponse> deleteReview(Long reviewId);

    ResponseEntity<ReviewDTO> getReviewForGameByUser(Long gameId);
}
