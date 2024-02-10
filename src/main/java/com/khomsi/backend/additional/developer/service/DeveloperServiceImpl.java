package com.khomsi.backend.additional.developer.service;

import com.khomsi.backend.additional.developer.DeveloperRepository;
import com.khomsi.backend.additional.developer.model.entity.Developer;
import lombok.RequiredArgsConstructor;
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
}
