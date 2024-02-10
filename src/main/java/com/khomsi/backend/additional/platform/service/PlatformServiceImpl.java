package com.khomsi.backend.additional.platform.service;

import com.khomsi.backend.additional.platform.PlatformRepository;
import com.khomsi.backend.additional.platform.model.entity.Platform;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlatformServiceImpl implements PlatformService {
    private final PlatformRepository platformRepository;

    @Override
    public List<Platform> getAllPlatforms() {
        return platformRepository.findAll();
    }
}
