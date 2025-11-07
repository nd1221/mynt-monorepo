package com.example.mynt_finance_backend.progressTracking.domain.entities;

import com.example.mynt_finance_backend.learningPlatform.domain.entities.QuestionEntity;
import com.example.mynt_finance_backend.util.customConverters.DurationToLongConverter;
import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(
        name = "question_progress_trackers",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"lesson_progress_tracker_id", "question_id"}
        ))
public class QuestionProgressTracker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(
            targetEntity = LessonProgressTracker.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "lesson_progress_tracker_id")
    private LessonProgressTracker lessonProgressTracker;

    @ManyToOne(
            targetEntity = QuestionEntity.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "question_id")
    private QuestionEntity question;

    private int totalCount;

    private int correctCount;

    private int correctStreak;

    private double accuracy;

    private LocalDate firstReviewedDate;

    private LocalDate lastReviewedDate;

    private LocalDate nextReviewDate;

    @Convert(converter = DurationToLongConverter.class)
    private Duration averageQuestionTime;

    private int repetitions;

    private double easinessFactor;

    private int interval;

    private int userRating;

    @OneToMany(
            targetEntity = QuestionReview.class,
            mappedBy = "questionProgressTracker",
            fetch = FetchType.LAZY
    )
    private Set<QuestionReview> questionReviews;

    @ManyToOne(
            targetEntity = AggregateQuestionTracker.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "aggregate_question_tracker_id")
    private AggregateQuestionTracker aggregateQuestionTracker;

    public QuestionProgressTracker() {
    }

    public QuestionProgressTracker(Long id, LessonProgressTracker lessonProgressTracker, QuestionEntity question, int totalCount, int correctCount, int correctStreak, double accuracy, LocalDate firstReviewedDate, LocalDate lastReviewedDate, LocalDate nextReviewDate, Duration averageQuestionTime, int repetitions, double easinessFactor, int interval, int userRating, Set<QuestionReview> questionReviews) {
        this.id = id;
        this.lessonProgressTracker = lessonProgressTracker;
        this.question = question;
        this.totalCount = totalCount;
        this.correctCount = correctCount;
        this.correctStreak = correctStreak;
        this.accuracy = accuracy;
        this.firstReviewedDate = firstReviewedDate;
        this.lastReviewedDate = lastReviewedDate;
        this.nextReviewDate = nextReviewDate;
        this.averageQuestionTime = averageQuestionTime;
        this.repetitions = repetitions;
        this.easinessFactor = easinessFactor;
        this.interval = interval;
        this.userRating = userRating;
        this.questionReviews = questionReviews;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LessonProgressTracker getLessonProgressTracker() {
        return lessonProgressTracker;
    }

    public void setLessonProgressTracker(LessonProgressTracker lessonProgressTracker) {
        this.lessonProgressTracker = lessonProgressTracker;
    }

    public QuestionEntity getQuestion() {
        return question;
    }

    public void setQuestion(QuestionEntity question) {
        this.question = question;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public void incrementCount() {
        this.totalCount++;
    }

    public int getCorrectCount() {
        return correctCount;
    }

    public void setCorrectCount(int correctCount) {
        this.correctCount = correctCount;
    }

    public void incrementCorrectCount() {
        this.correctCount++;
    }

    public int getCorrectStreak() {
        return correctStreak;
    }

    public void setCorrectStreak(int correctStreak) {
        this.correctStreak = correctStreak;
    }

    public void incrementStreak() {
        this.correctStreak++;
    }

    public void resetStreak() {
        this.correctStreak = 0;
    }

    public LocalDate getFirstReviewedDate() {
        return firstReviewedDate;
    }

    public void setFirstReviewedDate(LocalDate firstReviewedDate) {
        this.firstReviewedDate = firstReviewedDate;
    }

    public LocalDate getLastReviewedDate() {
        return lastReviewedDate;
    }

    public void setLastReviewedDate(LocalDate lastReviewedDate) {
        this.lastReviewedDate = lastReviewedDate;
    }

    public LocalDate getNextReviewDate() {
        return nextReviewDate;
    }

    public void setNextReviewDate(LocalDate nextReviewDate) {
        this.nextReviewDate = nextReviewDate;
    }

    public Duration getAverageQuestionTime() {
        return averageQuestionTime;
    }

    public void setAverageQuestionTime(Duration averageQuestionTime) {
        this.averageQuestionTime = averageQuestionTime;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public void incrementRepetitions() { this.repetitions++; }

    public void resetRepetitions() { this.repetitions = 0; }

    public double getEasinessFactor() {
        return easinessFactor;
    }

    public void setEasinessFactor(double easinessFactor) {
        this.easinessFactor = easinessFactor;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getUserRating() {
        return userRating;
    }

    public void setUserRating(int userRating) {
        this.userRating = userRating;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public Set<QuestionReview> getQuestionReviews() {
        return questionReviews;
    }

    public void setQuestionReviews(Set<QuestionReview> questionReviews) {
        this.questionReviews = questionReviews;
    }

    public AggregateQuestionTracker getAggregateQuestionTracker() {
        return aggregateQuestionTracker;
    }

    public void setAggregateQuestionTracker(AggregateQuestionTracker aggregateQuestionTracker) {
        this.aggregateQuestionTracker = aggregateQuestionTracker;
    }
}
