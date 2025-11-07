package com.example.mynt_finance_backend.progressTracking.controllers;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.CourseDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.LessonDTO;
import com.example.mynt_finance_backend.learningPlatform.services.*;
import com.example.mynt_finance_backend.progressTracking.controllers.utils.QuestionBankSearchParam;
import com.example.mynt_finance_backend.progressTracking.domain.dtos.*;
import com.example.mynt_finance_backend.progressTracking.services.*;
import com.example.mynt_finance_backend.util.commonDTOs.EntityTrackerDTO;
import com.example.mynt_finance_backend.util.commonDTOs.TitlePositionDTO;
import com.example.mynt_finance_backend.util.validation.ControllerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/progress")
public class ProgressController {

    private final UserProgressTrackerService userProgressTrackerService;

    private final UserService userService;

    private final CourseProgressTrackerService courseProgressTrackerService;

    private final LessonProgressTrackerService lessonProgressTrackerService;

    private final QuestionProgressTrackerService questionProgressTrackerService;

    private final TestProgressTrackerService testProgressTrackerService;

    private final CourseService courseService;

    private final LessonService lessonService;

    private final QuestionService questionService;

    private final TestService testService;

    private final QuestionBankService questionBankService;

    private final AggregateQuestionTrackerService aggregateQuestionTrackerService;

    @Autowired
    public ProgressController(UserProgressTrackerService userProgressTrackerService, UserService userService, CourseProgressTrackerService courseProgressTrackerService, LessonProgressTrackerService lessonProgressTrackerService, QuestionProgressTrackerService questionProgressTrackerService, TestProgressTrackerService testProgressTrackerService, CourseService courseService, LessonService lessonService, TestService testService, QuestionBankService questionBankService, QuestionService questionService, AggregateQuestionTrackerService aggregateQuestionTrackerService) {
        this.userProgressTrackerService = userProgressTrackerService;
        this.userService = userService;
        this.courseProgressTrackerService = courseProgressTrackerService;
        this.lessonProgressTrackerService = lessonProgressTrackerService;
        this.questionProgressTrackerService = questionProgressTrackerService;
        this.testProgressTrackerService = testProgressTrackerService;
        this.courseService = courseService;
        this.lessonService = lessonService;
        this.testService = testService;
        this.questionBankService = questionBankService;
        this.questionService = questionService;
        this.aggregateQuestionTrackerService = aggregateQuestionTrackerService;
    }

    @GetMapping("/user-tracker/{userProgressTrackerId}")
    public ResponseEntity<UserProgressTrackerDTO> getUserProgressTrackerById(@PathVariable("userProgressTrackerId") Long userProgressTrackerId) {
        ControllerValidator.exists(userProgressTrackerService::exists, userProgressTrackerId).validate();
        return new ResponseEntity<>(userProgressTrackerService.getUserProgressTrackerById(userProgressTrackerId), HttpStatus.OK);
    }

    @GetMapping("/{userId}/tracker")
    public ResponseEntity<UserProgressTrackerDTO> getUserProgressTrackerByUserId(@PathVariable("userId") Long userId) {
        ControllerValidator.exists(userService::exists, userId).validate();
        return new ResponseEntity<>(userProgressTrackerService.getUserProgressTrackerByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/course-tracker/{courseTrackerId}")
    public ResponseEntity<CourseProgressTrackerDTO> getCourseProgressTracker(
            @PathVariable("courseTrackerId") Long courseTrackerId
    ) {
        ControllerValidator.exists(courseProgressTrackerService::exists, courseTrackerId).validate();
        return new ResponseEntity<>(courseProgressTrackerService.readById(courseTrackerId), HttpStatus.OK);
    }

    @GetMapping("/course-tracker-{userProgressTrackerId}/{courseId}")
    public ResponseEntity<CourseProgressTrackerDTO> getCourseProgressTracker(
            @PathVariable("userProgressTrackerId") Long userProgressTrackerId,
            @PathVariable("courseId") Integer courseId
    ) {
        ControllerValidator.exists(userProgressTrackerService::exists, userProgressTrackerId)
                .and(ControllerValidator.exists(courseService::exists, courseId))
                .validate();
        return new ResponseEntity<>(courseProgressTrackerService.readByCourseId(userProgressTrackerId, courseId), HttpStatus.OK);
    }

    @GetMapping("/lesson-tracker/{lessonTrackerId}")
    public ResponseEntity<LessonProgressTrackerDTO> getLessonProgressTracker(
            @PathVariable("lessonTrackerId") Long lessonTrackerId
    ) {
        ControllerValidator.exists(lessonProgressTrackerService::exists, lessonTrackerId).validate();
        return new ResponseEntity<>(lessonProgressTrackerService.readById(lessonTrackerId), HttpStatus.OK);
    }

    @GetMapping("lesson-overview-data/{lessonTrackerId}")
    public ResponseEntity<NavigableEntityTrackerDTO<LessonDTO, LessonProgressTrackerDTO>> getLessonOverviewData(
            @PathVariable("lessonTrackerId") Long lessonTrackerId
    ) {
        ControllerValidator.exists(lessonProgressTrackerService::exists, lessonTrackerId).validate();
        return new ResponseEntity<>(lessonProgressTrackerService.getLessonOverviewData(lessonTrackerId), HttpStatus.OK);
    }

    @GetMapping("/{courseProgressTrackerId}/lesson-{lessonId}/lesson-tracker")
    public ResponseEntity<LessonProgressTrackerDTO> createLessonProgressTracker(
            @PathVariable("courseProgressTrackerId") Long courseProgressTrackerId,
            @PathVariable("lessonId") Integer lessonId
    ) {
        ControllerValidator.exists(lessonService::exists, lessonId)
                .and(ControllerValidator.exists(courseProgressTrackerService::exists, courseProgressTrackerId))
                .validate();
        return new ResponseEntity<>(lessonProgressTrackerService.createLessonProgressTracker(courseProgressTrackerId, lessonId), HttpStatus.OK);
    }

    @GetMapping("/create-question-tracker/{courseTrackerId}/{lessonId}/{aggregateTrackerId}/{questionId}")
    public ResponseEntity<Void> createQuestionProgressTracker(
            @PathVariable("courseTrackerId") Long courseTrackerId,
            @PathVariable("lessonId") Integer lessonId,
            @PathVariable("aggregateTrackerId") Long aggregateTrackerId,
            @PathVariable("questionId") Long questionId
    ) {
        ControllerValidator.exists(courseProgressTrackerService::exists, courseTrackerId)
                .and(ControllerValidator.exists(lessonService::exists, lessonId))
                .and(ControllerValidator.exists(aggregateQuestionTrackerService::exists, aggregateTrackerId))
                .and(ControllerValidator.exists(questionService::exists, questionId))
                .validate();
        questionProgressTrackerService.createQuestionProgressTracker(courseTrackerId, lessonId, questionId, aggregateTrackerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get-question-tracker/{courseTrackerId}/{lessonId}/{questionId}")
    public ResponseEntity<QuestionProgressTrackerDTO> getQuestionProgressTrackerByCourseTrackerAndLessonAndQuestion(
            @PathVariable("courseTrackerId") Long courseTrackerId,
            @PathVariable("lessonId") Integer lessonId,
            @PathVariable("questionId") Long questionId
    ) {
        ControllerValidator.exists(courseProgressTrackerService::exists, courseTrackerId)
                .and(ControllerValidator.exists(lessonService::exists, lessonId))
                .and(ControllerValidator.exists(questionService::exists, questionId))
                .validate();
        return new ResponseEntity<>(questionProgressTrackerService.getByCourseTrackerAndLessonAndQuestion(courseTrackerId, lessonId, questionId), HttpStatus.OK);
    }

    @GetMapping("create-aggregate-question-tracker/{questionId}")
    public ResponseEntity<Void> createAggregateQuestionTracker(
            @PathVariable("questionId") Long questionId
    ) {
        ControllerValidator.exists(questionService::exists, questionId).validate();
        aggregateQuestionTrackerService.createAggregateQuestionTracker(questionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("get-aggregate-question-tracker/{questionId}")
    public ResponseEntity<AggregateQuestionTrackerDTO> getAggregateQuestionTrackerByQuestionId(
            @PathVariable("questionId") Long questionId
    ) {
        ControllerValidator.exists(questionService::exists, questionId).validate();
        return new ResponseEntity<>(aggregateQuestionTrackerService.getAggregateTrackerByQuestionId(questionId), HttpStatus.OK);
    }

    @PatchMapping("/update-aggregate-question-tracker/{aggregateQuestionTrackerId}")
    public ResponseEntity<AggregateQuestionTrackerDTO> updateAggregateQuestionTracker (
            @PathVariable("aggregateQuestionTrackerId") Long aggregateQuestionTrackerId,
            @RequestBody AggregateQuestionUpdateDTO aggregateQuestionUpdateDTO
    ) {
        ControllerValidator.exists(aggregateQuestionTrackerService::exists, aggregateQuestionTrackerId).validate();
        return new ResponseEntity<>(aggregateQuestionTrackerService.updateAggregateQuestionTracker(aggregateQuestionTrackerId, aggregateQuestionUpdateDTO), HttpStatus.OK);
    }

    @GetMapping("/question-tracker/{questionTrackerId}")
    public ResponseEntity<QuestionProgressTrackerDTO> getQuestionProgressTracker(
            @PathVariable("questionTrackerId") Long questionTrackerId
    ) {
        ControllerValidator.exists(questionProgressTrackerService::exists, questionTrackerId).validate();
        return new ResponseEntity<>(questionProgressTrackerService.readById(questionTrackerId), HttpStatus.OK);
    }

    @PatchMapping("/update-question/{questionProgressTrackerId}")
    public ResponseEntity<QuestionProgressTrackerDTO> updateQuestionProgress(
            @PathVariable("questionProgressTrackerId") Long questionProgressTrackerId,
            @RequestBody QuestionProgressUpdateDTO updateDTO
    ) {
        ControllerValidator.exists(questionProgressTrackerService::exists, questionProgressTrackerId).validate();
        QuestionProgressTrackerDTO dto = questionProgressTrackerService.updateQuestionProgress(questionProgressTrackerId, updateDTO);
        return dto == null ?
                new ResponseEntity<>(questionProgressTrackerService.readById(questionProgressTrackerId), HttpStatus.NOT_MODIFIED) :
                new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PatchMapping("/update-user-rating/{questionProgressTrackerId}")
    public ResponseEntity<Void> updateUserRating(
            @PathVariable("questionProgressTrackerId") Long questionProgressTrackerId,
            @RequestBody Integer newRating
    ) {
        ControllerValidator.exists(questionProgressTrackerService::exists, questionProgressTrackerId).validate();
        questionProgressTrackerService.updateUserRating(questionProgressTrackerId, newRating);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/update-test/{testId}/{courseProgressTrackerId}")
    public ResponseEntity<TestProgressTrackerDTO> updateTestProgress(
            @PathVariable("testId") Integer testId,
            @PathVariable("courseProgressTrackerId") Long courseProgressTrackerId,
            @RequestBody TestProgressUpdateDTO updateDTO
    ) {
        ControllerValidator.exists(courseProgressTrackerService::exists, courseProgressTrackerId)
                .and(ControllerValidator.exists(testService::exists, testId))
                .validate();
        TestProgressTrackerDTO dto = testProgressTrackerService.updateTestProgress(courseProgressTrackerId, testId, updateDTO);
        return dto == null ?
                new ResponseEntity<>(testProgressTrackerService.readByCourseProgressTrackerAndTest(courseProgressTrackerId, testId), HttpStatus.NOT_MODIFIED) :
                new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PatchMapping("/remember-course/{userProgressTrackerId}/{mostRecentCourseTrackerId}")
    public ResponseEntity<Void> rememberMostRecentCourse(
            @PathVariable("userProgressTrackerId") Long userProgressTrackerId,
            @PathVariable("mostRecentCourseTrackerId") Long mostRecentCourseTrackerId
    ) {
        ControllerValidator.exists(courseProgressTrackerService::exists, mostRecentCourseTrackerId)
                .and(ControllerValidator.exists(userProgressTrackerService::exists, userProgressTrackerId))
                .validate();
        userProgressTrackerService.setMostRecentCourseTracker(userProgressTrackerId, mostRecentCourseTrackerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/course-lesson-mastery-completeness/{courseProgressTrackerId}")
    public ResponseEntity<List<SectionMasteryCompletenessDTO>> getCourseLessonMasteryCompleteness(
            @PathVariable("courseProgressTrackerId") Long courseProgressTrackerId
    ) {
        ControllerValidator.exists(courseProgressTrackerService::exists, courseProgressTrackerId).validate();
        return new ResponseEntity<>(courseProgressTrackerService.getMasteryCompletenessForAllLessons(courseProgressTrackerId), HttpStatus.OK);
    }

    @GetMapping("/course-entity-tracker/{courseProgressTrackerId}")
    public ResponseEntity<EntityTrackerDTO<CourseDTO, CourseProgressTrackerDTO>> getCourseEntityTrackerPair(
            @PathVariable("courseProgressTrackerId") Long courseProgressTrackerId
    ) {
        ControllerValidator.exists(courseProgressTrackerService::exists, courseProgressTrackerId).validate();
        return new ResponseEntity<>(courseProgressTrackerService.getCourseEntityTrackerPair(courseProgressTrackerId), HttpStatus.OK);
    }

    @GetMapping("/course-search-summary/{userProgressTrackerId}")
    public ResponseEntity<List<CourseTrackerSearchSummaryDTO>> getAllCourseTrackerSummaries(
            @PathVariable("userProgressTrackerId") Long userProgressTrackerId
    ) {
        ControllerValidator.exists(userProgressTrackerService::exists, userProgressTrackerId).validate();
        return new ResponseEntity<>(userProgressTrackerService.getCourseTrackerSearchSummaries(userProgressTrackerId), HttpStatus.OK);
    }

    @GetMapping("/lesson-search-summary/{courseProgressTrackerId}")
    public ResponseEntity<List<LessonTrackerSearchSummaryDTO>> getAllLessonTrackerSummariesInCourseTracker(
            @PathVariable("courseProgressTrackerId") Long courseProgressTrackerId
    ) {
        ControllerValidator.exists(courseProgressTrackerService::exists, courseProgressTrackerId).validate();
        return new ResponseEntity<>(courseProgressTrackerService.getLessonTrackerSearchSummaries(courseProgressTrackerId), HttpStatus.OK);
    }

    @GetMapping("/course-section-list/{courseProgressTrackerId}")
    public ResponseEntity<List<TitlePositionDTO<Integer>>> getSectionTitlesAndPositionsByCourseTracker(
            @PathVariable("courseProgressTrackerId") Long courseProgressTrackerId
    ) {
        ControllerValidator.exists(courseProgressTrackerService::exists, courseProgressTrackerId).validate();
        return new ResponseEntity<>(courseProgressTrackerService.getSectionTitlesAndPositionsByTrackerId(courseProgressTrackerId), HttpStatus.OK);
    }

    @GetMapping("/question-bank/{courseProgressTrackerId}")
    public ResponseEntity<QuestionBankPageDTO> getQuestionBankQuestionsByCourseTracker(
            @PathVariable("courseProgressTrackerId") Long courseProgressTrackerId,
            @ModelAttribute QuestionBankSearchParam searchParams,
            Pageable pageable
    ) {
        ControllerValidator.exists(courseProgressTrackerService::exists, courseProgressTrackerId).validate();
        return new ResponseEntity<>(questionBankService.getFilteredQuestions(pageable, searchParams, courseProgressTrackerId), HttpStatus.OK);
    }

    @GetMapping("/course-progress/{courseProgressTrackerId}/question-bank/aggregate")
    public ResponseEntity<CourseProgressQuestionStatisticsDTO> getQuestionBankCourseQuestionsCompleted(
            @PathVariable("courseProgressTrackerId") Long courseProgressTrackerId
    ) {
        ControllerValidator.exists(courseProgressTrackerService::exists, courseProgressTrackerId).validate();
        return new ResponseEntity<>(courseProgressTrackerService.getQuestionStatisticsPerDifficulty(courseProgressTrackerId), HttpStatus.OK);
    }
}