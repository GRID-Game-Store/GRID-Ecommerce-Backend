package com.khomsi.backend.main.admin.service;

import com.khomsi.backend.main.admin.model.request.EntityModelRequest;
import com.khomsi.backend.main.admin.model.request.ReviewRequest;
import com.khomsi.backend.main.admin.model.response.AdminModelResponse;
import com.khomsi.backend.main.admin.model.response.AdminResponse;

public interface AdminReviewService {
    AdminModelResponse getAllReviewsByGame(Long gameId, EntityModelRequest entityModelRequest);

    //TODO test methods above
    AdminResponse editReview(ReviewRequest reviewRequest);

    AdminResponse deleteReview(Long reviewId);
}
