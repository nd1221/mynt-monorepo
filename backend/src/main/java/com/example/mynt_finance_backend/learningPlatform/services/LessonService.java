package com.example.mynt_finance_backend.learningPlatform.services;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.LessonContentDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.LessonDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.LessonEntity;
import com.example.mynt_finance_backend.util.baseInterfaces.Service;

import java.util.List;
import java.util.Set;

public interface LessonService extends Service<Integer> {

    boolean hasQuestion(Integer lessonId, Long questionId);

    // CREATE
    LessonDTO createLesson(LessonDTO lessonDTO);

    // READ
    LessonDTO readLessonById(Integer id);

    List<LessonDTO> readAllLessons();

    Set<LessonEntity> findAllLessonsById(Set<Integer> lessonIds);

    LessonEntity findLessonById(Integer id);

    LessonContentDTO getLessonContent(Integer id);

    int getNumberOfCoreQuestions(int lessonId);

    // UPDATE
    LessonDTO updateLesson(LessonDTO lessonDTO, Integer id);

    LessonDTO addQuestion(Integer lessonId, Long questionId);

    LessonDTO removeQuestion(Integer lessonId, Long questionId);

    // DELETE
    void deleteLesson(Integer id);
}
