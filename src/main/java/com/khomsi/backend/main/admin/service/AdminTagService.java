package com.khomsi.backend.main.admin.service;

import com.khomsi.backend.main.admin.model.request.EntityEditRequest;
import com.khomsi.backend.main.admin.model.request.EntityInsertRequest;
import com.khomsi.backend.main.admin.model.response.AdminResponse;

public interface AdminTagService {
    AdminResponse addTag(EntityInsertRequest entityInsertRequest);

    AdminResponse editTag(EntityEditRequest entityEditRequest);

    AdminResponse deleteTag(Long tagId);
}
