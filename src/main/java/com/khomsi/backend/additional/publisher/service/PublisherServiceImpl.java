package com.khomsi.backend.additional.publisher.service;

import com.khomsi.backend.additional.publisher.PublisherRepository;
import com.khomsi.backend.additional.publisher.model.entity.Publisher;
import com.khomsi.backend.main.handler.exception.GlobalServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    @Override
    public Publisher findPublisherById(Long pubId) {
        return publisherRepository.findById(pubId).orElseThrow(()
                -> new GlobalServiceException(HttpStatus.NOT_FOUND, "Publisher with id " + pubId + " is not found."));
    }
}
