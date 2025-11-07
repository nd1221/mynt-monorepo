package com.example.mynt_finance_backend.learningPlatform.services.impl;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.LearningPathDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.LearningPathEntity;
import com.example.mynt_finance_backend.util.baseInterfaces.Mapper;
import com.example.mynt_finance_backend.learningPlatform.repositories.LearningPathRepository;
import com.example.mynt_finance_backend.learningPlatform.services.LearningPathService;
import com.example.mynt_finance_backend.learningPlatform.services.intermediaries.LearningPlatformServicesFacade;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class LearningPathServiceImpl implements LearningPathService {

    private final LearningPathRepository learningPathRepository;

    private final Mapper<LearningPathDTO, LearningPathEntity> learningPathMapper;

    private final LearningPlatformServicesFacade learningPlatformServicesFacade;

    @Autowired
    public LearningPathServiceImpl(LearningPathRepository learningPathRepository, Mapper<LearningPathDTO, LearningPathEntity> learningPathMapper, @Lazy LearningPlatformServicesFacade learningPlatformServicesFacade) {
        this.learningPathRepository = learningPathRepository;
        this.learningPathMapper = learningPathMapper;
        this.learningPlatformServicesFacade = learningPlatformServicesFacade;
    }

    @Override
    public boolean exists(Integer id) {
        return learningPathRepository.existsById(id);
    }

    @Override
    public boolean hasCourse(Integer learningPathId, Integer courseId) {
        return learningPathRepository.learningPathContainsCourse(learningPathId, courseId);
    }

    @Override
    public boolean hasTag(Integer learningPathId, Integer tagId) {
        return learningPathRepository.learningPathContainsTag(learningPathId, tagId);
    }

    // CREATE
    @Override
    public LearningPathDTO createLearningPath(LearningPathDTO learningPathDTO) {
        LearningPathEntity learningPathEntity = learningPathMapper.mapToEntity(learningPathDTO);
        return learningPathMapper.mapToDTO(learningPathRepository.save(learningPathEntity));
    }

    // READ
    @Override
    public LearningPathDTO readLearningPathById(Integer id) {
        Optional<LearningPathEntity> learningPathEntity = learningPathRepository.findById(id);
        return learningPathEntity.map(learningPathMapper::mapToDTO).orElse(null);
    }

    @Override
    public List<LearningPathDTO> readAllLearningPaths() {
        return learningPathRepository.findAll().stream().map(learningPathMapper::mapToDTO).toList();
    }

    @Override
    public Set<LearningPathEntity> findAllLearningPaths() {
        return new HashSet<>(learningPathRepository.findAll());
    }

    @Override
    public Set<LearningPathEntity> findAllLearningPathsById(Set<Integer> learningPathIds) {
        return learningPathIds == null ?
                null :
                new HashSet<>(learningPathRepository.findAllById(learningPathIds));
    }

    @Override
    public Optional<LearningPathEntity> findLearningPathById(Integer id) {
        return learningPathRepository.findById(id);
    }

    @Override
    public Page<LearningPathDTO> readAll(Pageable pageable) {
        Page<LearningPathEntity> learningPathEntities = learningPathRepository.findAll(pageable);
        return learningPathEntities.map(learningPathMapper::mapToDTO);
    }

    // UPDATE
    @Override
    public LearningPathDTO updateLearningPath(LearningPathDTO learningPathDTO, Integer id) {

        LearningPathEntity existingEntity = learningPathRepository.findById(id).get();

        updateFieldIfNotNull(existingEntity::setTitle, learningPathDTO.title());
        updateFieldIfNotNull(existingEntity::setDescription, learningPathDTO.description());
        updateFieldIfNotNull(existingEntity::setDifficulty, learningPathDTO.difficulty());
        updateFieldIfNotNull(existingEntity::setCreators, learningPathDTO.creators());
        updateFieldIfNotNull(existingEntity::setIconURL, learningPathDTO.iconURL());
        existingEntity.update();

        return learningPathMapper.mapToDTO(learningPathRepository.save(existingEntity));
    }

    @Override
    public LearningPathDTO addCourse(Integer learningPathId, Integer courseId) {
        LearningPathEntity learningPath = learningPathRepository.findById(learningPathId).get();
        learningPlatformServicesFacade.addCourseToLearningPath(learningPath, courseId);
        learningPath.update();
        return learningPathMapper.mapToDTO(learningPathRepository.save(learningPath));
    }

    @Override
    public LearningPathDTO removeCourse(Integer learningPathId, Integer courseId) {
        LearningPathEntity learningPath = learningPathRepository.findById(learningPathId).get();
        learningPlatformServicesFacade.removeCourseFromLearningPath(learningPath, courseId);
        learningPath.update();
        return learningPathMapper.mapToDTO(learningPathRepository.save(learningPath));
    }

    @Override
    public LearningPathDTO addTag(Integer learningPathId, Integer tagId) {
        LearningPathEntity learningPath = learningPathRepository.findById(learningPathId).get();
        learningPlatformServicesFacade.addTagToLearningPath(learningPath, tagId);
        return learningPathMapper.mapToDTO(learningPathRepository.save(learningPath));
    }

    @Override
    public LearningPathDTO removeTag(Integer learningPathId, Integer tagId) {
        LearningPathEntity learningPath = learningPathRepository.findById(learningPathId).get();
        learningPlatformServicesFacade.removeTagFromLearningPath(learningPath, tagId);
        return learningPathMapper.mapToDTO(learningPathRepository.save(learningPath));
    }

    // DELETE
    @Override
    public void deleteLearningPath(Integer id) {
        LearningPathEntity learningPath = learningPathRepository.findById(id).get();
        learningPlatformServicesFacade.deleteLearningPathChildren(learningPath);
        learningPlatformServicesFacade.removeLearningPathParentAssociations(learningPath);
        learningPathRepository.deleteById(id);
    }
}
