package com.khomsi.backend.main.admin.service;

import com.khomsi.backend.main.admin.model.request.EntityModelRequest;
import com.khomsi.backend.main.admin.model.response.AdminModelResponse;

public interface AdminTransactionService {
    AdminModelResponse getAllTransactions(EntityModelRequest entityModelRequest);
}
