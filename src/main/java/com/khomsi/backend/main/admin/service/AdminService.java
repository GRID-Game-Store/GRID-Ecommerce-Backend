package com.khomsi.backend.main.admin.service;

import com.khomsi.backend.main.admin.model.dto.GameInsertDTO;
import com.khomsi.backend.main.admin.model.response.AdminResponse;

public interface AdminService {
    AdminResponse addGameToDb(GameInsertDTO gameDTO);

    AdminResponse toggleGameActiveStatus(Long gameId, boolean active);
}
