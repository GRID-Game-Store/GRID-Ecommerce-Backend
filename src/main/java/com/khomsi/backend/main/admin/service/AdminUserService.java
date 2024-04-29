package com.khomsi.backend.main.admin.service;

import com.khomsi.backend.main.admin.model.request.EntityModelRequest;
import com.khomsi.backend.main.admin.model.response.AdminModelResponse;
import com.khomsi.backend.main.admin.model.response.AdminResponse;

import java.math.BigDecimal;

public interface AdminUserService {
    AdminModelResponse getAllUsers(EntityModelRequest entityModelRequest);
    AdminResponse updateUserBalance(String userId, BigDecimal newBalance);
}
