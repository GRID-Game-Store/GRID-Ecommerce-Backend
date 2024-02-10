package com.khomsi.backend.additional.publisher.service;

import com.khomsi.backend.additional.publisher.PublisherRepository;
import com.khomsi.backend.additional.publisher.model.entity.Publisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublisherServiceImpl implements PublisherService {
    private final PublisherRepository publisherRepository;

    @Override
    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }
}
