package com.example.mynt_finance_backend.progressTracking.domain.entities;

import com.example.mynt_finance_backend.learningPlatform.domain.entities.LessonEntity;
import com.example.mynt_finance_backend.util.customConverters.DurationToLongConverter;
import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(
        name = "lesson_progress_trackers",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"lesson_id", "course_progress_tracker_id"}
        )
)
public class LessonProgressTracker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(
            targetEntity = LessonEntity.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "lesson_id")
    private LessonEntity lesson;

    @OneToMany(
            targetEntity = QuestionProgressTracker.class,
            mappedBy = "lessonProgressTracker",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE
    )
    private Set<QuestionProgressTracker> questionProgressTrackers;

    @ManyToOne(
            targetEntity = CourseProgressTracker.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "course_progress_tracker_id")
    private CourseProgressTracker courseProgressTracker;

    private int streak;

    private int totalQuestionsReviewed;

    private double accuracy;

    @Convert(converter = DurationToLongConverter.class)
    private Duration averageQuestionTime;

    private int questionsDueToday;

    private LocalDate firstPracticeDate;

    private LocalDate lastPracticeDate;

    @Column(nullable = true) // Temp for development
    private double completeness;

    @Column(nullable = true) // Temp for development
    private double mastery;

    public LessonProgressTracker() {}

    public LessonProgressTracker(Long id, LessonEntity lesson, Set<QuestionProgressTracker> questionProgressTrackers, CourseProgressTracker courseProgressTracker, int streak, int totalQuestionsReviewed, double accuracy, Duration averageQuestionTime, int questionsDueToday, LocalDate firstPracticeDate, LocalDate lastPracticeDate, double completeness, double mastery) {
        this.id = id;
        this.lesson = lesson;
        this.questionProgressTrackers = questionProgressTrackers;
        this.courseProgressTracker = courseProgressTracker;
        this.streak = streak;
        this.totalQuestionsReviewed = totalQuestionsReviewed;
        this.accuracy = accuracy;
        this.averageQuestionTime = averageQuestionTime;
        this.questionsDueToday = questionsDueToday;
        this.firstPracticeDate = firstPracticeDate;
        this.lastPracticeDate = lastPracticeDate;
        this.completeness = completeness;
        this.mastery = mastery;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<QuestionProgressTracker> getQuestionProgressTrackers() {
        return questionProgressTrackers;
    }

    public void setQuestionProgressTrackers(Set<QuestionProgressTracker> questionProgressTrackers) {
        this.questionProgressTrackers = questionProgressTrackers;
    }

    public void addQuestionProgressTracker(QuestionProgressTracker qpt) {
        if (this.questionProgressTrackers == null) {
            this.questionProgressTrackers = new HashSet<>();
        }
        this.questionProgressTrackers.add(qpt);
    }

    public CourseProgressTracker getCourseProgressTracker() {
        return courseProgressTracker;
    }

    public void setCourseProgressTracker(CourseProgressTracker courseProgressTracker) {
        this.courseProgressTracker = courseProgressTracker;
    }

    public int getTotalQuestionsReviewed() {
        return totalQuestionsReviewed;
    }

    public void setTotalQuestionsReviewed(int totalQuestionsReviewed) {
        this.totalQuestionsReviewed = totalQuestionsReviewed;
    }

    public void incrementTotalQuestionsReviews() { this.totalQuestionsReviewed++; }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public Duration getAverageQuestionTime() {
        return averageQuestionTime;
    }

    public void setAverageQuestionTime(Duration averageQuestionTime) {
        this.averageQuestionTime = averageQuestionTime;
    }

    public int getQuestionsDueToday() {
        return questionsDueToday;
    }

    public void setQuestionsDueToday(int questionsDueToday) {
        this.questionsDueToday = questionsDueToday;
    }

    public LocalDate getFirstPracticeDate() {
        return firstPracticeDate;
    }

    public void setFirstPracticeDate(LocalDate firstPracticeDate) {
        this.firstPracticeDate = firstPracticeDate;
    }

    public LocalDate getLastPracticeDate() {
        return lastPracticeDate;
    }

    public void setLastPracticeDate(LocalDate    lastPracticeDate) {
        this.lastPracticeDate = lastPracticeDate;
    }

    public LessonEntity getLesson() {
        return lesson;
    }

    public void setLesson(LessonEntity lesson) {
        this.lesson = lesson;
    }

    public double getCompleteness() {
        return completeness;
    }

    public void setCompleteness(double completeness) {
        this.completeness = completeness;
    }

    public double getMastery() {
        return mastery;
    }

    public void setMastery(double mastery) {
        this.mastery = mastery;
    }

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }
}
