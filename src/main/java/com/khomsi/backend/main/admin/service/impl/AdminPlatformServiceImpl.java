package com.khomsi.backend.main.admin.service.impl;

import com.khomsi.backend.additional.platform.model.entity.Platform;
import com.khomsi.backend.additional.platform.service.PlatformService;
import com.khomsi.backend.main.admin.model.request.EntityEditRequest;
import com.khomsi.backend.main.admin.model.request.EntityInsertRequest;
import com.khomsi.backend.main.admin.model.response.AdminResponse;
import com.khomsi.backend.main.admin.service.AdminPlatformService;
import com.khomsi.backend.main.handler.exception.GlobalServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminPlatformServiceImpl implements AdminPlatformService {
    private final PlatformService platformService;

    @Override
    public AdminResponse addPlatform(EntityInsertRequest entityInsertRequest) {
        String name = entityInsertRequest.name();
        checkIfPlatformNameAlreadyExists(name);
        Platform platform = new Platform();
        platform.setName(name);
        platformService.savePlatformToDb(platform);
        return AdminResponse.builder().response("Platform with id " + platform.getId() + " is created!").build();
    }

    @Override
    public AdminResponse editPlatform(EntityEditRequest entityEditRequest) {
        Platform platform = platformService.getPlatformById(entityEditRequest.id());
        String newName = entityEditRequest.name();
        checkIfPlatformNameAlreadyExists(newName);
        platform.setName(newName);
        platformService.savePlatformToDb(platform);
        return AdminResponse.builder().response("Platform with id " + platform.getId() + " is edited!").build();
    }

    @Override
    public AdminResponse deletePlatform(Long platformId) {
        Platform platform = platformService.getPlatformById(platformId);
        platformService.deletePlatform(platform);
        return AdminResponse.builder().response("Platform with id " + platform.getId() + " is deleted!").build();
    }
    private void checkIfPlatformNameAlreadyExists(String newPlatformName) {
        if (platformService.isPlatformNameExistsIgnoreCase(newPlatformName)) {
            throw new GlobalServiceException(HttpStatus.BAD_REQUEST, "Platform with name '" + newPlatformName + "' already exists!");
        }
    }
}
