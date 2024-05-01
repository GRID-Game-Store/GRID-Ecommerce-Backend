package com.khomsi.backend.additional.developer.service;

import com.khomsi.backend.additional.developer.DeveloperRepository;
import com.khomsi.backend.additional.developer.model.entity.Developer;
import com.khomsi.backend.main.handler.exception.GlobalServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeveloperServiceImpl implements DeveloperService {
    private final DeveloperRepository developerRepository;

    @Override
    public List<Developer> getAllDevelopers() {
        return developerRepository.findAll();
    }

    @Override
    public Developer findDeveloperById(Long devId) {
        return developerRepository.findById(devId).orElseThrow(()
                -> new GlobalServiceException(HttpStatus.NOT_FOUND, "Developer with id " + devId + " is not found."));
    }
}
