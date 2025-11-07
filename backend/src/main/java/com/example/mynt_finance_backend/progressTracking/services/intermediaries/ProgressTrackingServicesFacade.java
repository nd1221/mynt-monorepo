package com.example.mynt_finance_backend.progressTracking.services.intermediaries;

import com.example.mynt_finance_backend.learningPlatform.domain.entities.*;
import com.example.mynt_finance_backend.progressTracking.domain.dtos.AggregateQuestionTrackerDTO;
import com.example.mynt_finance_backend.progressTracking.domain.dtos.CourseTrackerSearchSummaryDTO;
import com.example.mynt_finance_backend.progressTracking.domain.dtos.PracticeQuestionDTO;
import com.example.mynt_finance_backend.progressTracking.domain.dtos.QuestionProgressTrackerDTO;
import com.example.mynt_finance_backend.progressTracking.domain.entities.*;
import com.example.mynt_finance_backend.progressTracking.services.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProgressTrackingServicesFacade {

    @PersistenceContext
    private final EntityManager entityManager;

    private final UserProgressTrackerService userProgressTrackerService;

    private final CourseProgressTrackerService courseProgressTrackerService;

    private final LessonProgressTrackerService lessonProgressTrackerService;

    private final QuestionProgressTrackerService questionProgressTrackerService;

    private final AggregateQuestionTrackerService aggregateQuestionTrackerService;

    @Autowired
    public ProgressTrackingServicesFacade(EntityManager entityManager, UserProgressTrackerService userProgressTrackerService, CourseProgressTrackerService courseProgressTrackerService, LessonProgressTrackerService lessonProgressTrackerService, QuestionProgressTrackerService questionProgressTrackerService, AggregateQuestionTrackerService aggregateQuestionTrackerService) {
        this.entityManager = entityManager;
        this.userProgressTrackerService = userProgressTrackerService;
        this.courseProgressTrackerService = courseProgressTrackerService;
        this.lessonProgressTrackerService = lessonProgressTrackerService;
        this.questionProgressTrackerService = questionProgressTrackerService;
        this.aggregateQuestionTrackerService = aggregateQuestionTrackerService;
    }

    public void createProgressTracker(UserEntity user) {
        userProgressTrackerService.createUserProgressTracker(user);
    }

    public void setCourseTrackerActive(UserProgressTracker upt, int courseId) {
        upt.getCourseProgressTrackers().forEach(cpt -> {
            if (cpt.getCourse().getId() == courseId) {
                cpt.activate();
            }
        });
    }

    public void setCourseTrackerInactive(UserProgressTracker upt, CourseEntity course) {
        courseProgressTrackerService.deactivate(upt.getId(), course.getId());
    }

    public CourseProgressTracker findCourseProgressTrackerById(Long courseProgressTrackerId) {
        return courseProgressTrackerService.findById(courseProgressTrackerId).get();
    }

    public void updateLessonProgressTracker(LessonProgressTracker lpt, long questionId, boolean questionCorrect, long questionTime) {
        lessonProgressTrackerService.updateLessonProgressTracker(lpt, questionId, questionCorrect, questionTime);
    }

    public void updateCourseProgressTracker(CourseProgressTracker cpt, boolean questionCorrect, long questionTime) {
        courseProgressTrackerService.updateCourseProgressTracker(cpt, questionCorrect, questionTime);
    }

    public List<PracticeQuestionDTO> getDueQuestions(long lessonProgressTrackerId, int numberRequested) {
        return questionProgressTrackerService.getDueQuestions(lessonProgressTrackerId, numberRequested);
    }

    public List<PracticeQuestionDTO> getQuestions(long lessonProgressTrackerId) {
        return questionProgressTrackerService.getQuestions(lessonProgressTrackerId);
    }

    public List<PracticeQuestionDTO> getQuestions(long lessonProgressTrackerId, int numberRequested) {
        return questionProgressTrackerService.getQuestions(lessonProgressTrackerId, numberRequested);
    }

    public void setReviewQPT(long questionProgressTrackerId, QuestionReview review) {
        QuestionProgressTracker qpt = questionProgressTrackerService.readEntityById(questionProgressTrackerId);
        review.setQuestionProgressTracker(qpt);
    }

    public void setReviewUPT(long userProgressTrackerId, QuestionReview review) {
        UserProgressTracker upt = userProgressTrackerService.findUserProgressTrackerById(userProgressTrackerId);
        review.setUserProgressTracker(upt);
    }

    public UserProgressTracker getUserProgressTrackerById(long userProgressTrackerId) {
        return userProgressTrackerService.findUserProgressTrackerById(userProgressTrackerId);
    }

    public LessonProgressTracker findLessonProgressTrackerById(long lptId) {
        return lessonProgressTrackerService.findLessonProgressTrackerById(lptId);
    }

    public void updateCompleteness(LessonProgressTracker lpt, boolean isCore) {
        lessonProgressTrackerService.updateCompleteness(lpt, isCore);
    }

    public UserProgressTracker findUserProgressTrackerById(long uptId) {
        return userProgressTrackerService.findUserProgressTrackerById(uptId);
    }

    public long getNumberOfQuestionsDueForLessonProgressTracker(long lptId) {
        return lessonProgressTrackerService.getNumberOfQuestionsDueToday(lptId);
    }

    public CourseTrackerSearchSummaryDTO getCourseTrackerSummary(long courseProgressTrackerId) {
        return courseProgressTrackerService.getSearchSummary(courseProgressTrackerId);
    }

    public QuestionProgressTrackerDTO getQuestionProgressTrackerIfExists(long questionId, long courseProgressTrackerId) {
        return questionProgressTrackerService.readByCourseTrackerAndQuestionIdIfExists(questionId, courseProgressTrackerId);
    }

    public AggregateQuestionTrackerDTO getAggregateTrackerByQuestionId(long questionId) {
        return aggregateQuestionTrackerService.getAggregateTrackerByQuestionId(questionId);
    }

    public Long findTotalCourseQuestionsByDifficulty(int courseId, double lowerThreshold, double upperThreshold) {
        return aggregateQuestionTrackerService.findTotalCourseQuestionsByDifficulty(courseId, lowerThreshold, upperThreshold);
    }

    public Long findTotalCourseQuestionsAnsweredByCourse(int courseId, long courseTrackerId) {
        return questionProgressTrackerService.findTotalCourseQuestionsAnsweredByCourse(courseId, courseTrackerId);
    }

    public Long findTotalCourseQuestionsAnsweredByCourseTrackerAndDifficulty(int courseId, long courseTrackerId, double lowerThreshold, double upperThreshold) {
        return questionProgressTrackerService.findTotalCourseQuestionsAnsweredByCourseTrackerAndDifficulty(courseId, courseTrackerId, lowerThreshold, upperThreshold);
    }

    public LessonProgressTracker findLessonProgressTrackerByCourseTrackerAndLesson(long courseTrackerId, int lessonId) {
        return courseProgressTrackerService.getLessonTrackerByLessonId(courseTrackerId, lessonId);
    }

    public void updateMostRecentCourse(long userProgressTrackerId) {
        userProgressTrackerService.setMostRecentCourseTrackerAfterDeactivation(userProgressTrackerId);
    }

    public AggregateQuestionTracker findAggregateQuestionTrackerById(long id) {
        return aggregateQuestionTrackerService.findAggregateQuestionTrackerById(id);
    }
}
