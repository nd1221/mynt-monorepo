package com.example.mynt_finance_backend.progressTracking.controllers;

import com.example.mynt_finance_backend.progressTracking.domain.dtos.PracticeQuestionDTO;
import com.example.mynt_finance_backend.progressTracking.domain.dtos.ReviewQuickstartDTO;
import com.example.mynt_finance_backend.progressTracking.domain.dtos.SectionReviewQuickstartDTO;
import com.example.mynt_finance_backend.progressTracking.services.CourseProgressTrackerService;
import com.example.mynt_finance_backend.progressTracking.services.LessonProgressTrackerService;
import com.example.mynt_finance_backend.progressTracking.services.UserProgressTrackerService;
import com.example.mynt_finance_backend.util.validation.ControllerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/practice")
public class PracticeController {

    private final CourseProgressTrackerService courseProgressTrackerService;

    private final LessonProgressTrackerService lessonProgressTrackerService;

    private final UserProgressTrackerService userProgressTrackerService;

    @Autowired
    public PracticeController(CourseProgressTrackerService courseProgressTrackerService, LessonProgressTrackerService lessonProgressTrackerService, UserProgressTrackerService userProgressTrackerService) {
        this.courseProgressTrackerService = courseProgressTrackerService;
        this.lessonProgressTrackerService = lessonProgressTrackerService;
        this.userProgressTrackerService = userProgressTrackerService;
    }

    @GetMapping("/lesson-progress/{lessonProgressTrackerId}/questions-due")
    public ResponseEntity<List<PracticeQuestionDTO>> getDueQuestions(
            @PathVariable("lessonProgressTrackerId") Long lessonProgressTrackerId
    ) {
        ControllerValidator.exists(lessonProgressTrackerService::exists, lessonProgressTrackerId).validate();
        return new ResponseEntity<>(lessonProgressTrackerService.getDueQuestions(lessonProgressTrackerId), HttpStatus.OK);
    }

    @GetMapping("/lesson-progress/{lessonProgressTrackerId}/questions-{numberRequested}")
    public ResponseEntity<List<PracticeQuestionDTO>> getQuestions(
            @PathVariable("lessonProgressTrackerId") Long lessonProgressTrackerId,
            @PathVariable("numberRequested") Integer numberRequested
    ) {
        ControllerValidator.exists(lessonProgressTrackerService::exists, lessonProgressTrackerId).validate();
        return new ResponseEntity<>(lessonProgressTrackerService.getQuestions(lessonProgressTrackerId, numberRequested), HttpStatus.OK);
    }

    @GetMapping("/lesson-progress/{lessonProgressTrackerId}/questions-all")
    public ResponseEntity<List<PracticeQuestionDTO>> getAllQuestions(
            @PathVariable("lessonProgressTrackerId") Long lessonProgressTrackerId
    ) {
        ControllerValidator.exists(lessonProgressTrackerService::exists, lessonProgressTrackerId).validate();
        return new ResponseEntity<>(lessonProgressTrackerService.getQuestions(lessonProgressTrackerId), HttpStatus.OK);
    }

    @GetMapping("/course-review-quickstart/{userProgressTrackerId}")
    public ResponseEntity<List<ReviewQuickstartDTO>> getCourseReviewQuickstartData(
            @PathVariable("userProgressTrackerId") Long userProgressTrackerId
    ) {
        ControllerValidator.exists(userProgressTrackerService::exists, userProgressTrackerId).validate();
        return new ResponseEntity<>(courseProgressTrackerService.getAllCourseReviewsDueToday(userProgressTrackerId), HttpStatus.OK);
    }

    @GetMapping("/lessons-review-quickstart/{courseProgressTrackerId}")
    public ResponseEntity<List<ReviewQuickstartDTO>> getAllLessonsReviewQuickstartData(
            @PathVariable("courseProgressTrackerId") Long courseProgressTrackerId
    ) {
        ControllerValidator.exists(courseProgressTrackerService::exists, courseProgressTrackerId).validate();
        return new ResponseEntity<>(courseProgressTrackerService.getAllLessonReviewsDueTodayByCourse(courseProgressTrackerId), HttpStatus.OK);
    }

    @GetMapping("/section-review-quickstart/{courseProgressTrackerId}")
    public ResponseEntity<List<SectionReviewQuickstartDTO>> getAllLessonsReviewQuickstartDataBySection(
            @PathVariable("courseProgressTrackerId") Long courseProgressTrackerId
    ) {
        ControllerValidator.exists(courseProgressTrackerService::exists, courseProgressTrackerId).validate();
        return new ResponseEntity<>(courseProgressTrackerService.getAllLessonReviewsDueTodayBySection(courseProgressTrackerId), HttpStatus.OK);
    }
}
