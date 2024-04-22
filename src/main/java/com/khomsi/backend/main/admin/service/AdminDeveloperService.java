package com.khomsi.backend.main.admin.service;

import com.khomsi.backend.main.admin.model.request.EntityEditRequest;
import com.khomsi.backend.main.admin.model.response.AdminResponse;

public interface AdminDeveloperService {
    AdminResponse editDeveloper(EntityEditRequest entityEditRequest);

    AdminResponse deleteDeveloper(Long developerId);
}
