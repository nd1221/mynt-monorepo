package com.example.mynt_finance_backend.progressTracking.controllers;

import com.example.mynt_finance_backend.progressTracking.controllers.utils.CustomSessionSearchParam;
import com.example.mynt_finance_backend.progressTracking.controllers.utils.ReviewSessionSearchParam;
import com.example.mynt_finance_backend.progressTracking.domain.dtos.*;
import com.example.mynt_finance_backend.progressTracking.services.*;
import com.example.mynt_finance_backend.util.validation.ControllerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {

    private final QuestionReviewService questionReviewService;

    private final QuestionProgressTrackerService questionProgressTrackerService;

    private final CourseProgressTrackerService courseProgressTrackerService;

    private final LessonProgressTrackerService lessonProgressTrackerService;

    private final TestProgressTrackerService testProgressTrackerService;

    private final UserProgressTrackerService userProgressTrackerService;

    private final ReviewSessionService reviewSessionService;

    @Autowired
    public ReviewController(QuestionReviewService questionReviewService, QuestionProgressTrackerService questionProgressTrackerService, CourseProgressTrackerService courseProgressTrackerService, LessonProgressTrackerService lessonProgressTrackerService, TestProgressTrackerService testProgressTrackerService, UserProgressTrackerService userProgressTrackerService, ReviewSessionService reviewSessionService) {
        this.questionReviewService = questionReviewService;
        this.questionProgressTrackerService = questionProgressTrackerService;
        this.courseProgressTrackerService = courseProgressTrackerService;
        this.lessonProgressTrackerService = lessonProgressTrackerService;
        this.testProgressTrackerService = testProgressTrackerService;
        this.userProgressTrackerService = userProgressTrackerService;
        this.reviewSessionService = reviewSessionService;
    }

    @GetMapping("/question/past-year/{questionProgressTrackerId}")
    public ResponseEntity<List<QuestionReviewDTO>> getQuestionReviewsPastYear(
            @PathVariable("questionProgressTrackerId") Long questionProgressTrackerId
    ) {
        ControllerValidator.exists(questionProgressTrackerService::exists, questionProgressTrackerId).validate();
        return new ResponseEntity<>(questionReviewService.getReviewsPastYear(questionProgressTrackerId, 12), HttpStatus.OK);
    }

    @GetMapping("/test/past-year/{testProgressTrackerId}")
    public ResponseEntity<List<TestReviewDTO>> getTestReviewsPastYear(
            @PathVariable("testProgressTrackerId") Long testProgressTrackerId
    ) {
        ControllerValidator.exists(testProgressTrackerService::exists, testProgressTrackerId).validate();
        return new ResponseEntity<>(testProgressTrackerService.getReviewsPastN(testProgressTrackerId, 12), HttpStatus.OK);
    }

    @GetMapping("/courses/past-year/{courseProgressTrackerId}")
    public ResponseEntity<List<QuestionReviewDTO>> getCourseReviews(
            @PathVariable("courseProgressTrackerId") Long courseProgressTrackerId
    ) {
        ControllerValidator.exists(courseProgressTrackerService::exists, courseProgressTrackerId).validate();
        return new ResponseEntity<>(questionReviewService.getCourseReviews(courseProgressTrackerId), HttpStatus.OK);
    }

    @GetMapping("/lessons/past-year/{lessonProgressTrackerId}")
    public ResponseEntity<List<QuestionReviewDTO>> getLessonReviews(
            @PathVariable("lessonProgressTrackerId") Long lessonProgressTrackerId
    ) {
        ControllerValidator.exists(lessonProgressTrackerService::exists, lessonProgressTrackerId).validate();
        return new ResponseEntity<>(questionReviewService.getLessonReviews(lessonProgressTrackerId), HttpStatus.OK);
    }

    @GetMapping("/lessons/{lessonProgressTrackerId}/past-{n}-months/{questionDifficulty}")
    public ResponseEntity<List<QuestionReviewDTO>> getLessonReviewsPastNMonths(
            @PathVariable("lessonProgressTrackerId") Long lessonProgressTrackerId,
            @PathVariable("n") Integer n,
            @PathVariable("questionDifficulty") Integer difficulty
    ) {
        ControllerValidator.exists(lessonProgressTrackerService::exists, lessonProgressTrackerId).validate();
        return new ResponseEntity<>(questionReviewService.getLessonReviewsWithParams(lessonProgressTrackerId, n, difficulty), HttpStatus.OK);
    }

    @PostMapping("/{userProgressTrackerId}/question/{questionProgressTrackerId}")
    public ResponseEntity<Void> createReview(
            @PathVariable("userProgressTrackerId") Long userProgressTrackerId,
            @PathVariable("questionProgressTrackerId") Long questionProgressTrackerId,
            @RequestBody QuestionReviewDTO dto
    ) {
        ControllerValidator.exists(questionProgressTrackerService::exists, questionProgressTrackerId)
                .and(ControllerValidator.exists(userProgressTrackerService::exists, userProgressTrackerId))
                .validate();
        questionReviewService.createReview(questionProgressTrackerId, userProgressTrackerId, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/aggregate/recent-activity/{userProgressTrackerId}")
    public ResponseEntity<List<AggregateReviewDTO>> getRecentActivity(
            @PathVariable("userProgressTrackerId") Long userProgressTrackerId
    ) {
        ControllerValidator.exists(userProgressTrackerService::exists, userProgressTrackerId).validate();
        return new ResponseEntity<>(questionReviewService.getRecentActivityAggregateReviews(userProgressTrackerId), HttpStatus.OK);
    }

    @GetMapping("/question-bank-metadata/{courseProgressTrackerId}")
    public ResponseEntity<QuestionBankMetadataDTO> getQuestionBankMetadata(
            @PathVariable("courseProgressTrackerId") Long courseProgressTrackerId
    ) {
        ControllerValidator.exists(courseProgressTrackerService::exists, courseProgressTrackerId).validate();
        return new ResponseEntity<>(courseProgressTrackerService.getQuestionBankMetadataByTrackerId(courseProgressTrackerId), HttpStatus.OK);
    }

    @GetMapping("/load-session/{courseProgressTrackerId}")
    public ResponseEntity<ReviewSessionMetadataDTO  > loadReviewSessionData(
            @PathVariable("courseProgressTrackerId") Long courseProgressTrackerId,
            @ModelAttribute ReviewSessionSearchParam searchParam
    ) {
        ControllerValidator.exists(courseProgressTrackerService::exists, courseProgressTrackerId).validate();
        return new ResponseEntity<>(reviewSessionService.loadReviewSession(courseProgressTrackerId, searchParam), HttpStatus.OK);
    }

    @GetMapping("/load-custom-session/{courseProgressTrackerId}")
    public ResponseEntity<ReviewSessionMetadataDTO> loadCustomSessionData(
            @PathVariable("courseProgressTrackerId") Long courseProgressTrackerId,
            @ModelAttribute CustomSessionSearchParam searchParam
    ) {
        ControllerValidator.exists(courseProgressTrackerService::exists, courseProgressTrackerId).validate();
        return new ResponseEntity<>(reviewSessionService.loadCustomSession(courseProgressTrackerId, searchParam), HttpStatus.OK);
    }

    @GetMapping("/reviews-due-today/{courseProgressTrackerId}")
    public ResponseEntity<Long> getCourseReviewsDueToday(@PathVariable("courseProgressTrackerId") Long courseProgressTrackerId) {
        ControllerValidator.exists(courseProgressTrackerService::exists, courseProgressTrackerId).validate();
        return new ResponseEntity<>(courseProgressTrackerService.getReviewsDueToday(courseProgressTrackerId), HttpStatus.OK);
    }

    @PostMapping("/get-session/{courseProgressTrackerId}")
    public ResponseEntity<List<QuestionProgressAggregateDTO>> getSessionData(
            @PathVariable("courseProgressTrackerId") Long courseProgressTrackerId,
            @RequestBody ReviewSessionRequestDTO sessionRequest
    ) {
        ControllerValidator.exists(courseProgressTrackerService::exists, courseProgressTrackerId).validate();
        return new ResponseEntity<>(reviewSessionService.getSession(sessionRequest), HttpStatus.OK);
    }
}
