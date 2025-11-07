package com.example.mynt_finance_backend.learningPlatform.services;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.TagDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.TagEntity;
import com.example.mynt_finance_backend.util.baseInterfaces.Service;

import java.util.List;
import java.util.Set;

public interface TagService extends Service<Integer> {

    // CREATE
    TagDTO createTag(TagDTO tagDTO);

    // READ
    TagDTO readTagById(Integer id);

    List<TagDTO> readAllTags();

    TagEntity findTagById(Integer tagId);

    Set<TagEntity> findAllTagsById(Set<Integer> tagIds);

    List<TagEntity> findAllTagsByValue(List<String> tagValues);

    // UPDATE
    TagDTO updateTagById(Integer id, TagDTO tagDTO);

    // DELETE
    void deleteTagById(Integer id);
}
