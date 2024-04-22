package com.khomsi.backend.additional.tag.service;

import com.khomsi.backend.additional.tag.model.entity.Tag;
import jakarta.transaction.Transactional;

import java.util.List;

public interface TagService {
    List<Tag> getAllTags();

    Tag getTagById(Long tagId);

    void saveTagToDb(Tag tag);
    @Transactional
    void deleteTag(Tag tag);
}
