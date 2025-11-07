package com.example.mynt_finance_backend.learningPlatform.services;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.LearningPathDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.LearningPathEntity;
import com.example.mynt_finance_backend.util.baseInterfaces.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface LearningPathService extends Service<Integer> {

    boolean hasCourse(Integer learningPathId, Integer courseId);

    boolean hasTag(Integer learningPathId, Integer tagId);

    // CREATE
    LearningPathDTO createLearningPath(LearningPathDTO learningPathDTO);

    // READ
    LearningPathDTO readLearningPathById(Integer id);

    List<LearningPathDTO> readAllLearningPaths(); // Deprecated

    Set<LearningPathEntity> findAllLearningPaths();

    Set<LearningPathEntity> findAllLearningPathsById(Set<Integer> learningPathIds);

    Optional<LearningPathEntity> findLearningPathById(Integer id);

    Page<LearningPathDTO> readAll(Pageable pageable);

    // UPDATE
    LearningPathDTO updateLearningPath(LearningPathDTO learningPathDTO, Integer id);

    LearningPathDTO addCourse(Integer learningPathId, Integer courseId);

    LearningPathDTO removeCourse(Integer learningPathId, Integer courseId);

    LearningPathDTO addTag(Integer learningPathId, Integer tagId);

    LearningPathDTO removeTag(Integer learningPathId, Integer tagId);

    // DELETE
    void deleteLearningPath(Integer id);
}
