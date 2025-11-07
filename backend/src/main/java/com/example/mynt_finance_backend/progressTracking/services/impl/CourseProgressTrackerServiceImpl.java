package com.example.mynt_finance_backend.progressTracking.services.impl;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.CourseDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.CourseEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.LessonEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.SectionEntity;
import com.example.mynt_finance_backend.learningPlatform.services.intermediaries.LearningPlatformServicesFacade;
import com.example.mynt_finance_backend.progressTracking.domain.dtos.*;
import com.example.mynt_finance_backend.progressTracking.domain.entities.CourseProgressTracker;
import com.example.mynt_finance_backend.progressTracking.domain.entities.LessonProgressTracker;
import com.example.mynt_finance_backend.progressTracking.domain.entities.UserProgressTracker;
import com.example.mynt_finance_backend.progressTracking.mappers.CourseProgressTrackerMapper;
import com.example.mynt_finance_backend.progressTracking.repositories.CourseProgressTrackerRepository;
import com.example.mynt_finance_backend.progressTracking.services.CourseProgressTrackerService;
import com.example.mynt_finance_backend.progressTracking.services.intermediaries.ProgressTrackingServicesFacade;
import com.example.mynt_finance_backend.util.commonDTOs.EntityTrackerDTO;
import com.example.mynt_finance_backend.util.commonDTOs.ParentChildrenDTO;
import com.example.mynt_finance_backend.util.commonDTOs.TitlePositionDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

import static com.example.mynt_finance_backend.util.progressTrackingUtils.Difficulty.*;

@Service
@Transactional
public class CourseProgressTrackerServiceImpl implements CourseProgressTrackerService {

    private final CourseProgressTrackerRepository courseProgressTrackerRepository;

    private final CourseProgressTrackerMapper mapper;

    private final ProgressTrackingServicesFacade progressTrackingServicesFacade;

    private final LearningPlatformServicesFacade learningPlatformServicesFacade;

    @Autowired
    public CourseProgressTrackerServiceImpl(CourseProgressTrackerRepository courseProgressTrackerRepository, CourseProgressTrackerMapper mapper, @Lazy ProgressTrackingServicesFacade progressTrackingServicesFacade, @Lazy LearningPlatformServicesFacade learningPlatformServicesFacade) {
        this.courseProgressTrackerRepository = courseProgressTrackerRepository;
        this.mapper = mapper;
        this.progressTrackingServicesFacade = progressTrackingServicesFacade;
        this.learningPlatformServicesFacade = learningPlatformServicesFacade;
    }

    @Override
    public boolean exists(Long id) {
        return courseProgressTrackerRepository.existsById(id);
    }

    @Override
    public Optional<CourseProgressTracker> findById(Long courseProgressTrackerId) {
        return courseProgressTrackerRepository.findById(courseProgressTrackerId);
    }

    @Override
    public boolean existsByUserProgressTrackerAndCourse(Long userProgressTrackerId, Integer courseId) {
        long matchingCPTCount = courseProgressTrackerRepository.existsByUserProgressTrackerAndCourse(userProgressTrackerId, courseId);
        return matchingCPTCount > 0;
    }

    @Override
    public void activate(Long userProgressTrackerId, Integer courseId) {
        CourseProgressTracker cpt =  courseProgressTrackerRepository.findByUserProgressTrackerAndCourse(userProgressTrackerId, courseId);
        cpt.activate();
    }

    @Override
    public void deactivate(Long userProgressTrackerId, Integer courseId) {
        CourseProgressTracker cpt =  courseProgressTrackerRepository.findByUserProgressTrackerAndCourse(userProgressTrackerId, courseId);
        cpt.deactivate();
    }

    private CourseProgressTracker createCourseProgressTracker(UserProgressTracker upt, CourseEntity course) {
        CourseProgressTracker cpt = new CourseProgressTracker();
        cpt.setCourse(course);
        cpt.activate();
        cpt.setUserProgressTracker(upt);
        cpt.setLessonProgressTrackers(new HashSet<>());
        cpt.setTestProgressTrackers(new HashSet<>());
        cpt.setTotalQuestionsReviewed(0);
        cpt.setAccuracy(0);
        cpt.setAverageQuestionTime(Duration.ofMillis(0));
        cpt.setQuestionsDueToday(0);
        cpt.setFirstPracticeDate(null);
        cpt.setLastPracticeDate(null);
        return courseProgressTrackerRepository.save(cpt);
    }

    @Override
    public CourseProgressTrackerDTO readById(Long courseProgressTrackerId) {
        CourseProgressTracker cpt = courseProgressTrackerRepository.findById(courseProgressTrackerId).get();
        return mapper.mapToDTO(cpt, getCourseMastery(cpt.getId()), getCourseCompleteness(cpt.getId()));
    }
    @Override
    public CourseProgressTrackerDTO readByCourseId(long userProgressTrackerId, int courseId) {
        CourseProgressTracker cpt = courseProgressTrackerRepository.findByUserProgressTrackerAndCourse(userProgressTrackerId, courseId);
        if (cpt != null) {
            return cpt.isActive() ? mapper.mapToDTO(cpt, getCourseMastery(cpt.getId()), getCourseCompleteness(cpt.getId())) : null;
        } else {
            // Create new course progress tracker
            UserProgressTracker upt = progressTrackingServicesFacade.getUserProgressTrackerById(userProgressTrackerId);
            CourseEntity course = learningPlatformServicesFacade.getCourseById(courseId);
            CourseProgressTracker newCPT = createCourseProgressTracker(upt, course);
            return mapper.mapToDTO(newCPT, getCourseMastery(newCPT.getId()), getCourseCompleteness(newCPT.getId()));
        }
    }

    @Override
    public void updateCourseProgressTracker(CourseProgressTracker cpt, boolean questionCorrect, long questionTime) {

        cpt.setAverageQuestionTime(Duration.ofMillis(
                calculateRollingAverageCumulative(
                        cpt.getAverageQuestionTime().toMillis(),
                        questionTime,
                        cpt.getTotalQuestionsReviewed()
                )
        ));
        cpt.setAccuracy(
                calculateRollingAverageBoolean(
                        cpt.getAccuracy(),
                        cpt.getTotalQuestionsReviewed(),
                        questionCorrect
                )
        );
        cpt.incrementTotalQuestionsReviewed();

        LocalDate today = LocalDate.now();
        cpt.setLastPracticeDate(today);
        if (cpt.getFirstPracticeDate() == null) {
            cpt.setFirstPracticeDate(today);
        }

        cpt.setQuestionsDueToday(courseProgressTrackerRepository.getQuestionsDueToday(cpt.getId()));
    }

    private double getCourseMastery(long cptId) {
        Double courseMastery = courseProgressTrackerRepository.getAverageMastery(cptId);
        return courseMastery == null ? 0 : courseMastery;
    }

    private double getCourseCompleteness(long cptId) {
        Double courseCompleteness = courseProgressTrackerRepository.getAverageCompleteness(cptId);
        return courseCompleteness == null ? 0 : courseCompleteness;
    }

    @Override
    public List<ReviewQuickstartDTO> getAllCourseReviewsDueToday(long userProgressTrackerId) {
        UserProgressTracker upt = progressTrackingServicesFacade.findUserProgressTrackerById(userProgressTrackerId);
        return upt.getCourseProgressTrackers().stream()
                .map(cpt -> new ReviewQuickstartDTO(
                        cpt.getCourse().getId(),
                        cpt.getId(),
                        cpt.getCourse().getTitle(),
                        getAggregateCourseReviewsDue(cpt.getId())
                ))
                .sorted(
                        Comparator
                                .comparing(ReviewQuickstartDTO::reviewsDueToday)
                                .thenComparing(ReviewQuickstartDTO::title)
                )
                .toList();
    }

    @Override
    public List<ReviewQuickstartDTO> getAllLessonReviewsDueTodayByCourse(long courseProgressTrackerId) {
        return getIndividualLessonReviewStream(courseProgressTrackerId)
                .sorted(
                        Comparator
                                .comparing(ReviewQuickstartDTO::reviewsDueToday)
                                .thenComparing(ReviewQuickstartDTO::title)
                )
                .toList();
    }

    @Override
    public List<SectionReviewQuickstartDTO> getAllLessonReviewsDueTodayBySection(long courseProgressTrackerId) {
        CourseProgressTracker cpt = progressTrackingServicesFacade.findCourseProgressTrackerById(courseProgressTrackerId);

        return cpt.getCourse().getSections().stream()
                .map(section -> new SectionReviewQuickstartDTO(
                        section.getId(),
                        section.getPosition(),
                        section.getTitle(),
                        getLessonReviewQuickstartDTOsBySection(cpt, section.getId())
                ))
                .sorted(
                        Comparator.comparing(SectionReviewQuickstartDTO::sectionPosition)
                )
                .toList();
    }

    private List<ReviewQuickstartDTO> getLessonReviewQuickstartDTOsBySection(CourseProgressTracker cpt, int sectionId) {
        return cpt.getLessonProgressTrackers().stream()
                .filter(lpt -> lpt.getLesson().getSection().getId() == sectionId)
                .map(lpt -> new ReviewQuickstartDTO(
                        lpt.getLesson().getId(),
                        lpt.getId(),
                        lpt.getLesson().getTitle(),
                        (int) progressTrackingServicesFacade.getNumberOfQuestionsDueForLessonProgressTracker(lpt.getId())
                ))
                .sorted(
                        Comparator
                                .comparing(ReviewQuickstartDTO::reviewsDueToday)
                                .thenComparing(ReviewQuickstartDTO::title)
                )
                .toList();
    }

    private Stream<ReviewQuickstartDTO> getIndividualLessonReviewStream(long courseProgressTrackerId) {
        CourseProgressTracker cpt = courseProgressTrackerRepository.findById(courseProgressTrackerId).get();
        return cpt.getLessonProgressTrackers().stream()
                .map(lpt -> new ReviewQuickstartDTO(
                        lpt.getLesson().getId(),
                        lpt.getId(),
                        lpt.getLesson().getTitle(),
                        (int) progressTrackingServicesFacade.getNumberOfQuestionsDueForLessonProgressTracker(lpt.getId())
                ));
    }

    private int getAggregateCourseReviewsDue(long courseProgressTrackerId) {
        return getIndividualLessonReviewStream(courseProgressTrackerId)
                .map(ReviewQuickstartDTO::reviewsDueToday)
                .reduce(0, Integer::sum);
    }

    @Override
    public List<SectionMasteryCompletenessDTO> getMasteryCompletenessForAllLessons(long courseProgressTrackerId) {
        CourseProgressTracker cpt = courseProgressTrackerRepository.findById(courseProgressTrackerId).get();
        return cpt.getCourse().getSections().stream()
                .map(section -> new SectionMasteryCompletenessDTO(
                        section.getId(),
                        section.getTitle(),
                        section.getPosition(),
                        getMasteryCompletenessForLessonsInSection(section.getLessons(), cpt.getLessonProgressTrackers())
                ))
                .sorted(Comparator.comparing(SectionMasteryCompletenessDTO::sectionPosition))
                .toList();
    }

    private List<LessonMasteryCompletenessDTO> getMasteryCompletenessForLessonsInSection(Set<LessonEntity> lessons, Set<LessonProgressTracker> trackers) {
        return lessons.stream()
                .map(lesson -> mapToLessonMasteryCompletenessDTO(lesson, trackers))
                .sorted(Comparator.comparing(LessonMasteryCompletenessDTO::lessonPosition))
                .toList();
    }

    private LessonMasteryCompletenessDTO mapToLessonMasteryCompletenessDTO(LessonEntity lesson, Set<LessonProgressTracker> trackers) {

        Optional<LessonProgressTracker> optionalTracker = trackers.stream()
                .filter(tracker -> tracker.getLesson().getId().equals(lesson.getId()))
                .findFirst();

        if (optionalTracker.isPresent()) {
            LessonProgressTracker lpt = optionalTracker.get();
            return new LessonMasteryCompletenessDTO(
                    lesson.getId(),
                    lesson.getTitle(),
                    lesson.getPosition(),
                    true,
                    lpt.getId(),
                    lpt.getCompleteness(),
                    lpt.getMastery()
            );
        }

        return new LessonMasteryCompletenessDTO(
                lesson.getId(),
                lesson.getTitle(),
                lesson.getPosition(),
                false,
                null,
                0,
                0
        );
    }

    @Override
    public EntityTrackerDTO<CourseDTO, CourseProgressTrackerDTO> getCourseEntityTrackerPair(long courseProgressTrackerId) {
        CourseProgressTracker cpt = courseProgressTrackerRepository.findById(courseProgressTrackerId).get();
        return new EntityTrackerDTO<>(
                learningPlatformServicesFacade.getCourseDTOById(cpt.getCourse().getId()),
                mapper.mapToDTO(cpt, getCourseMastery(cpt.getId()), getCourseCompleteness(cpt.getId()))
        );
    }

    @Override
    public CourseTrackerSearchSummaryDTO getSearchSummary(long courseProgressTrackerId) {
        Optional<CourseProgressTracker> cpt = courseProgressTrackerRepository.findById(courseProgressTrackerId);
        if (cpt.isPresent()) {
            CourseProgressTracker tracker = cpt.get();
            return new CourseTrackerSearchSummaryDTO(
                    tracker.getId(),
                    tracker.getCourse().getTitle(),
                    tracker.getLastPracticeDate(),
                    getCourseMastery(tracker.getId()),
                    getCourseCompleteness(tracker.getId()),
                    tracker.getCourse().getIconURL()
            );
        }
        return null;
    }

    @Override
    public List<LessonTrackerSearchSummaryDTO> getLessonTrackerSearchSummaries(long courseProgressTrackerId) {
        CourseProgressTracker cpt = courseProgressTrackerRepository.findById(courseProgressTrackerId).get();
        String courseIconURL = cpt.getCourse().getIconURL();
        return cpt.getLessonProgressTrackers().stream()
                .map(lpt -> new LessonTrackerSearchSummaryDTO(
                        lpt.getId(),
                        lpt.getLesson().getId(),
                        lpt.getLesson().getTitle(),
                        lpt.getLesson().getSection().getTitle(),
                        lpt.getLesson().getSection().getPosition(),
                        lpt.getLesson().getPosition(),
                        lpt.getLastPracticeDate(),
                        lpt.getMastery(),
                        lpt.getCompleteness(),
                        courseIconURL
                ))
                .toList();
    }

    @Override
    public List<TitlePositionDTO<Integer>> getSectionTitlesAndPositionsByTrackerId(long courseProgressTrackerId) {
        CourseProgressTracker cpt = courseProgressTrackerRepository.findById(courseProgressTrackerId).get();
        return cpt.getCourse().getSections().stream()
                .map(section -> new TitlePositionDTO<>(
                        section.getId(),
                        section.getTitle(),
                        section.getPosition()
                ))
                .sorted(
                        Comparator.comparing(TitlePositionDTO::position)
                )
                .toList();
    }

    @Override
    public QuestionBankMetadataDTO getQuestionBankMetadataByTrackerId(long courseProgressTrackerId) {

        CourseProgressTracker cpt = courseProgressTrackerRepository.findById(courseProgressTrackerId).get();

        return new QuestionBankMetadataDTO(
                cpt.getCourse().getId(),
                cpt.getCourse().getTitle(),
                cpt.getCourse().getSections().stream()
                        .map(this::getQuestionBanksMetadataSections)
                        .sorted(
                                Comparator.comparing(section -> section.parent().position())
                        )
                        .toList()
        );
    }

    public ParentChildrenDTO<TitlePositionDTO<Integer>, TitlePositionDTO<Integer>> getQuestionBanksMetadataSections(SectionEntity section) {
        return new ParentChildrenDTO<>(
                new TitlePositionDTO<>(
                        section.getId(),
                        section.getTitle(),
                        section.getPosition()
                ),
                section.getLessons().stream()
                        .map(lesson -> new TitlePositionDTO<>(
                                lesson.getId(),
                                lesson.getTitle(),
                                lesson.getPosition()
                        ))
                        .sorted(
                                Comparator.comparing(TitlePositionDTO::position)
                        )
                        .toList()
        );
    }

    @Override
    public CourseProgressQuestionStatisticsDTO getQuestionStatisticsPerDifficulty(long courseProgressTrackerId) {

        CourseProgressTracker cpt = courseProgressTrackerRepository.findById(courseProgressTrackerId).get();
        int courseId = cpt.getCourse().getId();

        Long totalQuestions = learningPlatformServicesFacade.getNumberOfQuestionsInCourse(courseId);
        Long totalEasy = progressTrackingServicesFacade.findTotalCourseQuestionsByDifficulty(courseId, EASY.getThreshold(), UPPER_BOUND.getThreshold());
        Long totalMedium = progressTrackingServicesFacade.findTotalCourseQuestionsByDifficulty(courseId, MEDIUM.getThreshold(), EASY.getThreshold());
        Long totalHard = progressTrackingServicesFacade.findTotalCourseQuestionsByDifficulty(courseId, HARD.getThreshold(), MEDIUM.getThreshold());

        Long totalCompleted = progressTrackingServicesFacade.findTotalCourseQuestionsAnsweredByCourse(courseId, cpt.getId());
        Long easyCompleted = progressTrackingServicesFacade.findTotalCourseQuestionsAnsweredByCourseTrackerAndDifficulty(courseId, cpt.getId(), EASY.getThreshold(), UPPER_BOUND.getThreshold());
        Long mediumCompleted = progressTrackingServicesFacade.findTotalCourseQuestionsAnsweredByCourseTrackerAndDifficulty(courseId, cpt.getId(), MEDIUM.getThreshold(), EASY.getThreshold());
        Long hardCompleted = progressTrackingServicesFacade.findTotalCourseQuestionsAnsweredByCourseTrackerAndDifficulty(courseId, cpt.getId(), HARD.getThreshold(), MEDIUM.getThreshold());

        return new CourseProgressQuestionStatisticsDTO(
                totalQuestions,
                totalCompleted,
                totalEasy,
                easyCompleted,
                totalMedium,
                mediumCompleted,
                totalHard,
                hardCompleted
        );
    }

    @Override
    public boolean isComplete(long courseProgressTrackerId) {
        CourseProgressTracker cpt = courseProgressTrackerRepository.findById(courseProgressTrackerId).get();
        int totalLessons = cpt.getCourse().getNumberOfLessons();
        long completedLessonCount = cpt.getLessonProgressTrackers().stream()
                .filter(lpt -> lpt.getCompleteness() >= 1.0)
                .count();
        return completedLessonCount == totalLessons;
    }

    @Override
    public long getReviewsDueToday(long courseProgressTrackerId) {
        return courseProgressTrackerRepository.numberOfReviewsDueToday(courseProgressTrackerId, LocalDate.now());
    }

    @Override
    public LessonProgressTracker getLessonTrackerByLessonId(long courseTrackerId, int lessonId) {
        CourseProgressTracker cpt = courseProgressTrackerRepository.findById(courseTrackerId).get();
        return cpt.getLessonProgressTrackers().stream()
                .filter(lpt -> lpt.getLesson().getId() == lessonId)
                .findFirst()
                .orElse(null);
    }
}