package com.example.mynt_finance_backend.progressTracking.services;

import com.example.mynt_finance_backend.learningPlatform.domain.entities.QuestionEntity;
import com.example.mynt_finance_backend.progressTracking.domain.dtos.PracticeQuestionDTO;
import com.example.mynt_finance_backend.progressTracking.domain.dtos.QuestionProgressTrackerDTO;
import com.example.mynt_finance_backend.progressTracking.domain.dtos.QuestionProgressUpdateDTO;
import com.example.mynt_finance_backend.progressTracking.domain.entities.LessonProgressTracker;
import com.example.mynt_finance_backend.progressTracking.domain.entities.QuestionProgressTracker;
import com.example.mynt_finance_backend.util.baseInterfaces.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface QuestionProgressTrackerService extends Service<Long>, ProgressService {

    void createQuestionProgressTracker(long courseTrackerId, int lessonId, long questionId, long aggregateTrackerId);

    QuestionProgressTrackerDTO getByCourseTrackerAndLessonAndQuestion(long courseTrackerId, int lessonId, long questionId);

    QuestionProgressTrackerDTO readById(long questionProgressTrackerId);

    QuestionProgressTracker readEntityById(long questionProgressTrackerId);

    QuestionProgressTrackerDTO readByCourseTrackerAndQuestionIdIfExists(long questionId, long courseProgressTrackerId);

    QuestionProgressTrackerDTO updateQuestionProgress(long questionProgressTrackerId, QuestionProgressUpdateDTO updateDTO);

    void updateUserRating(long questionProgressTrackerId, int newRating);

    List<PracticeQuestionDTO> getDueQuestions(long lessonProgressTrackerId, int numberRequested);

    List<PracticeQuestionDTO> getQuestions(long lessonProgressTrackerId);

    List<PracticeQuestionDTO> getQuestions(long lessonProgressTrackerId, int numberRequested);

    Long findTotalCourseQuestionsAnsweredByCourse(int courseId, long courseTrackerId);

    Long findTotalCourseQuestionsAnsweredByCourseTrackerAndDifficulty(int courseId, long courseTrackerId, double lowerThreshold, double upperThreshold);

    List<QuestionProgressTracker> findAllBySpecification(Specification<QuestionProgressTracker> spec);

    Page<QuestionProgressTracker> findPageBySpecification(Specification<QuestionProgressTracker> spec, Pageable pageable);

    QuestionProgressTrackerDTO mapToDTO(QuestionProgressTracker qpt);
}
