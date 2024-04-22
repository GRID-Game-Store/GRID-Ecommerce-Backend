package com.khomsi.backend.main.admin.service;

import com.khomsi.backend.main.admin.model.request.EntityEditRequest;
import com.khomsi.backend.main.admin.model.request.EntityInsertRequest;
import com.khomsi.backend.main.admin.model.response.AdminResponse;

public interface AdminPlatformService {
    AdminResponse addPlatform(EntityInsertRequest entityInsertRequest);

    AdminResponse editPlatform(EntityEditRequest entityEditRequest);

    AdminResponse deletePlatform(Long tagId);
}
