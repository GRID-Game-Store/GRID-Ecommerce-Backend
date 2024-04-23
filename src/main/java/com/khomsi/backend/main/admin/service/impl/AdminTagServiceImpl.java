package com.khomsi.backend.main.admin.service.impl;

import com.khomsi.backend.additional.tag.model.entity.Tag;
import com.khomsi.backend.additional.tag.service.TagService;
import com.khomsi.backend.main.admin.model.request.EntityEditRequest;
import com.khomsi.backend.main.admin.model.request.EntityInsertRequest;
import com.khomsi.backend.main.admin.model.response.AdminResponse;
import com.khomsi.backend.main.admin.service.AdminTagService;
import com.khomsi.backend.main.handler.exception.GlobalServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminTagServiceImpl implements AdminTagService {
    private final TagService tagService;

    @Override
    public AdminResponse addTag(EntityInsertRequest entityInsertRequest) {
        String tagName = entityInsertRequest.name();
        checkIfTagNameAlreadyExists(tagName);
        Tag tag = new Tag();
        tag.setName(tagName);
        tagService.saveTagToDb(tag);

        return AdminResponse.builder().response("Tag with id " + tag.getId() + " is created!").build();
    }


    @Override
    public AdminResponse editTag(EntityEditRequest entityEditRequest) {
        Tag tag = tagService.getTagById(entityEditRequest.id());
        String newTagName = entityEditRequest.name();
        checkIfTagNameAlreadyExists(newTagName);
        tag.setName(newTagName);
        tagService.saveTagToDb(tag);
        return AdminResponse.builder().response("Tag with id " + tag.getId() + " is edited!").build();
    }

    @Override
    public AdminResponse deleteTag(Long tagId) {
        Tag tag = tagService.getTagById(tagId);
        tagService.deleteTag(tag);
        return AdminResponse.builder().response("Tag with id " + tag.getId() + " is deleted!").build();
    }

    private void checkIfTagNameAlreadyExists(String newTagName) {
        if (tagService.isTagNameExistsIgnoreCase(newTagName)) {
            throw new GlobalServiceException(HttpStatus.BAD_REQUEST, "Tag with name '" + newTagName + "' already exists!");
        }
    }
}
