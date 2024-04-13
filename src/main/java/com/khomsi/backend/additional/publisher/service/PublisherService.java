package com.khomsi.backend.additional.publisher.service;

import com.khomsi.backend.additional.publisher.model.entity.Publisher;

import java.util.List;

public interface PublisherService {
    List<Publisher> getAllPublishers();

    Publisher findPublisherById(Long pubId);
}
