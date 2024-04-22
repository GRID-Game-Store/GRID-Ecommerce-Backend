package com.khomsi.backend.main.admin.service;

import com.khomsi.backend.main.admin.model.request.EntityEditRequest;
import com.khomsi.backend.main.admin.model.request.EntityInsertRequest;
import com.khomsi.backend.main.admin.model.response.AdminResponse;

public interface AdminGenreService {
    AdminResponse addGenre(EntityInsertRequest entityInsertRequest);

    AdminResponse editGenre(EntityEditRequest entityEditRequest);

    AdminResponse deleteGenre(Long id);
}
