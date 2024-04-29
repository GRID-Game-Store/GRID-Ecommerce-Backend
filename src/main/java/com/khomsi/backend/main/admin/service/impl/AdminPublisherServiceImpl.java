package com.khomsi.backend.main.admin.service.impl;

import com.khomsi.backend.additional.publisher.PublisherRepository;
import com.khomsi.backend.additional.publisher.model.entity.Publisher;
import com.khomsi.backend.additional.publisher.service.PublisherService;
import com.khomsi.backend.main.admin.model.request.EntityEditRequest;
import com.khomsi.backend.main.admin.model.response.AdminResponse;
import com.khomsi.backend.main.admin.service.AdminPublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminPublisherServiceImpl implements AdminPublisherService {
    private final PublisherService publisherService;
    private final PublisherRepository publisherRepository;

    @Override
    public AdminResponse editPublisher(EntityEditRequest entityEditRequest) {
        Publisher publisher = publisherService.findPublisherById(entityEditRequest.id());
        publisher.setName(entityEditRequest.name());
        publisherRepository.save(publisher);
        return AdminResponse.builder().response("Publisher with id " + publisher.getId() + " is edited!").build();
    }

    @Override
    public AdminResponse deletePublisher(Long publisherId) {
        Publisher publisher = publisherService.findPublisherById(publisherId);
        publisherRepository.delete(publisher);
        return AdminResponse.builder().response("Publisher with id " + publisher.getId() + " is deleted!").build();
    }
}
