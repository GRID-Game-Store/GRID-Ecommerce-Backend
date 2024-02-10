package com.khomsi.backend.additional.tag.service;

import com.khomsi.backend.additional.tag.TagRepository;
import com.khomsi.backend.additional.tag.model.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }
}
