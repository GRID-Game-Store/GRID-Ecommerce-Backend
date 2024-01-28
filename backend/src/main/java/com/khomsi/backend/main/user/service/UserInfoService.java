package com.khomsi.backend.main.user.service;

import com.khomsi.backend.main.user.model.dto.FullUserInfoDTO;

public interface UserInfoService {
    FullUserInfoDTO getCurrentUser();
}
