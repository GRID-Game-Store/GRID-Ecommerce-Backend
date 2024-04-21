package com.khomsi.backend.main.admin.service;

import com.khomsi.backend.main.admin.model.request.EntityModelRequest;
import com.khomsi.backend.main.admin.model.response.AdminModelResponse;

public interface AdminReviewService {
    AdminModelResponse getAllReviewsByGame(Long gameId, EntityModelRequest entityModelRequest);
}
