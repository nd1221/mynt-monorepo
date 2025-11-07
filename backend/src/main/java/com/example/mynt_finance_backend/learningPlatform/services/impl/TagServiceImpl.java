package com.example.mynt_finance_backend.learningPlatform.services.impl;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.TagDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.TagEntity;
import com.example.mynt_finance_backend.learningPlatform.repositories.TagRepository;
import com.example.mynt_finance_backend.learningPlatform.services.TagService;
import com.example.mynt_finance_backend.util.baseInterfaces.Mapper;
import com.example.mynt_finance_backend.learningPlatform.services.intermediaries.LearningPlatformServicesFacade;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    private final Mapper<TagDTO, TagEntity> tagMapper;

    private final LearningPlatformServicesFacade learningPlatformServicesFacade;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository, Mapper<TagDTO, TagEntity> tagMapper, @Lazy LearningPlatformServicesFacade learningPlatformServicesFacade) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
        this.learningPlatformServicesFacade = learningPlatformServicesFacade;
    }

    @Override
    public boolean exists(Integer id) {
        return tagRepository.existsById(id);
    }

    // CREATE
    @Override
    public TagDTO createTag(TagDTO tagDTO) {
        TagEntity tagEntity = tagMapper.mapToEntity(tagDTO);
        return tagMapper.mapToDTO(tagRepository.save(tagEntity));
    }

    // READ
    @Override
    public TagDTO readTagById(Integer id) {
        return tagMapper.mapToDTO(tagRepository.findById(id).orElse(null));
    }

    @Override
    public List<TagDTO> readAllTags() {
        return tagRepository.findAll().stream()
                .map(tagMapper::mapToDTO)
                .toList();
    }

    @Override
    public List<TagEntity> findAllTagsByValue(List<String> tagValues) {
        return tagRepository.findAllTagsByValue(tagValues);
    }

    @Override
    public TagEntity findTagById(Integer tagId) {
        return tagRepository.findById(tagId).orElse(null);
    }

    @Override
    public Set<TagEntity> findAllTagsById(Set<Integer> tagIds) {
        return tagIds == null ?
                null :
                new HashSet<>(tagRepository.findAllById(tagIds));
    }

    // UPDATE
    @Override
    public TagDTO updateTagById(Integer id, TagDTO tagDTO) {

        TagEntity existingEntity = tagRepository.findById(id).get();
        updateFieldIfNotNull(existingEntity::setTag, tagDTO.tag());
        return tagMapper.mapToDTO(tagRepository.save(existingEntity));
    }

    // DELETE
    public void deleteTagById(Integer id) {
        tagRepository.deleteById(id);
    }
}
