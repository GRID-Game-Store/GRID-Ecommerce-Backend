package com.khomsi.backend.main.admin.service.impl;

import com.khomsi.backend.additional.review.ReviewRepository;
import com.khomsi.backend.additional.review.mapper.ReviewMapper;
import com.khomsi.backend.additional.review.model.dto.ReviewDTO;
import com.khomsi.backend.additional.review.model.entity.Review;
import com.khomsi.backend.main.admin.model.request.EntityModelRequest;
import com.khomsi.backend.main.admin.model.response.AdminModelResponse;
import com.khomsi.backend.main.admin.service.AdminReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.khomsi.backend.main.utils.SortingUtils.createSorting;

@Service
@RequiredArgsConstructor
public class AdminReviewServiceImpl implements AdminReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

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
}
