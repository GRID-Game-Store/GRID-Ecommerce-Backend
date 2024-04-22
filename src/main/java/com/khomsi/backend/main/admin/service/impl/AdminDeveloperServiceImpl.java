package com.khomsi.backend.main.admin.service.impl;

import com.khomsi.backend.additional.developer.DeveloperRepository;
import com.khomsi.backend.additional.developer.model.entity.Developer;
import com.khomsi.backend.additional.developer.service.DeveloperService;
import com.khomsi.backend.main.admin.model.request.EntityEditRequest;
import com.khomsi.backend.main.admin.model.response.AdminResponse;
import com.khomsi.backend.main.admin.service.AdminDeveloperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminDeveloperServiceImpl implements AdminDeveloperService {
    private final DeveloperService developerService;
    private final DeveloperRepository developerRepository;

    @Override
    public AdminResponse editDeveloper(EntityEditRequest entityEditRequest) {
        Developer developer = developerService.findDeveloperById(entityEditRequest.id());
        developer.setName(entityEditRequest.name());
        developerRepository.save(developer);
        return AdminResponse.builder().response("Developer with id " + developer.getId() + " is edited!").build();
    }

    @Override
    public AdminResponse deleteDeveloper(Long developerId) {
        Developer developer = developerService.findDeveloperById(developerId);
        developerRepository.delete(developer);
        return AdminResponse.builder().response("Developer with id " + developer.getId() + " is deleted!").build();
    }
}
