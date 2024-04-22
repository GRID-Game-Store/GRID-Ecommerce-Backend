package com.khomsi.backend.additional.platform.service;

import com.khomsi.backend.additional.platform.model.entity.Platform;
import jakarta.transaction.Transactional;

import java.util.List;

public interface PlatformService {
    List<Platform> getAllPlatforms();

    Platform getPlatformById(Long platformId);

    void savePlatformToDb(Platform platform);
    @Transactional
    void deletePlatform(Platform taplatform);
}
