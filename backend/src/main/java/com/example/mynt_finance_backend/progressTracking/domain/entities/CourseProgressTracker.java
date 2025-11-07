package com.example.mynt_finance_backend.progressTracking.domain.entities;

import com.example.mynt_finance_backend.learningPlatform.domain.entities.CourseEntity;
import com.example.mynt_finance_backend.util.customConverters.DurationToLongConverter;
import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(
        name = "course_progress_trackers",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"course_id", "user_progress_tracker_id"}
        )
)
public class CourseProgressTracker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(
            targetEntity = CourseEntity.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "course_id")
    private CourseEntity course;

    @ManyToOne(
            targetEntity = UserProgressTracker.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_progress_tracker_id")
    private UserProgressTracker userProgressTracker;

    @OneToMany(
            targetEntity = LessonProgressTracker.class,
            mappedBy = "courseProgressTracker",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE
    )
    private Set<LessonProgressTracker> lessonProgressTrackers;

    @OneToMany(
            targetEntity = TestProgressTracker.class,
            mappedBy = "courseProgressTracker",
            fetch = FetchType.LAZY
    )
    private Set<TestProgressTracker> testProgressTrackers;

    private int streak;

    private int totalQuestionsReviewed;

    private double accuracy;

    @Convert(converter = DurationToLongConverter.class)
    private Duration averageQuestionTime;

    private int questionsDueToday;

    private LocalDate firstPracticeDate;

    private LocalDate lastPracticeDate;

    private boolean isActive;

    private LocalDate deactivationDate;

    public CourseProgressTracker() {}

    public CourseProgressTracker(Long id, CourseEntity course, UserProgressTracker userProgressTracker, Set<LessonProgressTracker> lessonProgressTrackers, Set<TestProgressTracker> testProgressTrackers, int streak, int totalQuestionsReviewed, double accuracy, Duration averageQuestionTime, int questionsDueToday, LocalDate firstPracticeDate, LocalDate lastPracticeDate, boolean isActive) {
        this.id = id;
        this.course = course;
        this.userProgressTracker = userProgressTracker;
        this.lessonProgressTrackers = lessonProgressTrackers;
        this.testProgressTrackers = testProgressTrackers;
        this.streak = streak;
        this.totalQuestionsReviewed = totalQuestionsReviewed;
        this.accuracy = accuracy;
        this.averageQuestionTime = averageQuestionTime;
        this.questionsDueToday = questionsDueToday;
        this.firstPracticeDate = firstPracticeDate;
        this.lastPracticeDate = lastPracticeDate;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserProgressTracker getUserProgressTracker() {
        return userProgressTracker;
    }

    public void setUserProgressTracker(UserProgressTracker userProgressTracker) {
        this.userProgressTracker = userProgressTracker;
    }

    public Set<LessonProgressTracker> getLessonProgressTrackers() {
        return lessonProgressTrackers;
    }

    public void setLessonProgressTrackers(Set<LessonProgressTracker> lessonProgressTrackers) {
        this.lessonProgressTrackers = lessonProgressTrackers;
    }

    public void addLessonProgressTracker(LessonProgressTracker lpt) {
        if (this.lessonProgressTrackers == null) {
            this.lessonProgressTrackers = new HashSet<>();
        }
        this.lessonProgressTrackers.add(lpt);
    }

    public Set<TestProgressTracker> getTestProgressTrackers() {
        return testProgressTrackers;
    }

    public void setTestProgressTrackers(Set<TestProgressTracker> testProgressTrackers) {
        this.testProgressTrackers = testProgressTrackers;
    }

    public void addTestProgressTracker(TestProgressTracker tpt) {
        if (this.testProgressTrackers == null) {
            this.testProgressTrackers = new HashSet<>();
        }
        this.testProgressTrackers.add(tpt);
    }

    public int getTotalQuestionsReviewed() {
        return totalQuestionsReviewed;
    }

    public void setTotalQuestionsReviewed(int totalQuestionsReviewed) {
        this.totalQuestionsReviewed = totalQuestionsReviewed;
    }

    public void incrementTotalQuestionsReviewed() { this.totalQuestionsReviewed++; }

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

    public void setLastPracticeDate(LocalDate lastPracticeDate) {
        this.lastPracticeDate = lastPracticeDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void activate() {
        this.isActive = true;
        this.deactivationDate = LocalDate.now();
    }

    public void deactivate() {
        this.isActive = false;
    }

    public CourseEntity getCourse() {
        return course;
    }

    public void setCourse(CourseEntity course) {
        this.course = course;
    }

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    public LocalDate getDeactivationDate() {
        return deactivationDate;
    }

    public void setDeactivationDate(LocalDate deactivationDate) {
        this.deactivationDate = deactivationDate;
    }
}
