package com.khomsi.backend.additional.platform.service;

import com.khomsi.backend.additional.platform.PlatformRepository;
import com.khomsi.backend.additional.platform.model.entity.Platform;
import com.khomsi.backend.main.handler.exception.GlobalServiceException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @Override
    public Platform getPlatformById(Long platformId) {
        return platformRepository.findById(platformId).orElseThrow(()
                -> new GlobalServiceException(HttpStatus.NOT_FOUND, "Platform with id " + platformId + " is not found."));
    }

    @Override
    public void savePlatformToDb(Platform platform) {
        platformRepository.save(platform);
    }

    @Override
    @Transactional
    public void deletePlatform(Platform platform) {
        platformRepository.delete(platform);
    }
}
