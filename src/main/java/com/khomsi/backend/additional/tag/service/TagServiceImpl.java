package com.khomsi.backend.additional.tag.service;

import com.khomsi.backend.additional.tag.TagRepository;
import com.khomsi.backend.additional.tag.model.entity.Tag;
import com.khomsi.backend.main.handler.exception.GlobalServiceException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @Override
    public Tag getTagById(Long tagId) {
        return tagRepository.findById(tagId).orElseThrow(()
                -> new GlobalServiceException(HttpStatus.NOT_FOUND, "Tag with id " + tagId + " is not found."));
    }

    @Override
    public void saveTagToDb(Tag tag) {
        tagRepository.save(tag);
    }
    @Override
    @Transactional
    public void deleteTag(Tag tag) {
        tagRepository.delete(tag);
    }
}
