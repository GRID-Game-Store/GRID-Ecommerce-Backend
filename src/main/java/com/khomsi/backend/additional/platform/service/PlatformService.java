package com.khomsi.backend.additional.platform.service;

import com.khomsi.backend.additional.developer.model.entity.Developer;
import com.khomsi.backend.additional.platform.model.entity.Platform;

import java.util.List;

public interface PlatformService {
    List<Platform> getAllPlatforms();
}
