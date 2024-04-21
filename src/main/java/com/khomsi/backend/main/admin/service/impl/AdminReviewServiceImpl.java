package com.khomsi.backend.main.admin.service.impl;

import com.khomsi.backend.additional.review.ReviewRepository;
import com.khomsi.backend.additional.review.mapper.ReviewMapper;
import com.khomsi.backend.additional.review.model.dto.ReviewDTO;
import com.khomsi.backend.additional.review.model.entity.Review;
import com.khomsi.backend.additional.review.service.ReviewService;
import com.khomsi.backend.main.admin.model.request.EntityModelRequest;
import com.khomsi.backend.main.admin.model.request.ReviewRequest;
import com.khomsi.backend.main.admin.model.response.AdminModelResponse;
import com.khomsi.backend.main.admin.model.response.AdminResponse;
import com.khomsi.backend.main.admin.service.AdminReviewService;
import com.khomsi.backend.main.utils.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.khomsi.backend.main.utils.Utils.createSorting;
import static com.khomsi.backend.main.utils.Utils.truncateTitle;

@Service
@RequiredArgsConstructor
public class AdminReviewServiceImpl implements AdminReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final ReviewService reviewService;
    private final EmailService emailService;

    @Override
    public AdminModelResponse getAllReviewsByGame(Long gameId, EntityModelRequest entityModelRequest) {
        int page = entityModelRequest.getPage();
        Pageable pageable = PageRequest.of(page, entityModelRequest.getSize(),
                createSorting(entityModelRequest.getSort(), "reviewDate"));
        Page<Review> transactionPage = reviewRepository.findAllByGamesId(gameId, pageable);

        List<ReviewDTO> reviews = transactionPage.getContent().stream()
                .map(reviewMapper::toReviewToDTO)
                .toList();

        return AdminModelResponse.builder()
                .entities(reviews)
                .totalItems(transactionPage.getTotalElements())
                .totalPages(transactionPage.getTotalPages())
                .currentPage(page)
                .build();
    }

    @Override
    public AdminResponse editReview(ReviewRequest reviewRequest) {
        Review review = reviewService.getReview(reviewRequest.reviewId());
        String truncatedTitle = truncateTitle(review.getGames().getTitle());
        review.setComment(reviewRequest.comment());
        reviewRepository.save(review);
        emailService.sendWarningEmail("Your review on game `" + truncatedTitle +
                "` was edited by Admin on GRID!", review.getUsers());
        return AdminResponse.builder().response("Review with id " + review.getId() + " is edited!").build();
    }

    @Override
    public AdminResponse deleteReview(Long reviewId) {
        Review review = reviewService.getReview(reviewId);
        reviewRepository.delete(review);
        String truncatedTitle = truncateTitle(review.getGames().getTitle());
        emailService.sendWarningEmail("Your review on game `" + truncatedTitle +
                "` was deleted by Admin on GRID!", review.getUsers());
        return AdminResponse.builder().response("Review with id " + review.getId() + " is deleted!").build();
    }
}
