package com.example.mynt_finance_backend.progressTracking.domain.entities;

import com.example.mynt_finance_backend.util.customConverters.DurationToLongConverter;
import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDate;

@Entity
@Table(name = "question_reviews")
public class QuestionReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(
            targetEntity = UserProgressTracker.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_progress_tracker_id")
    private UserProgressTracker userProgressTracker;

    @ManyToOne(
            targetEntity = QuestionProgressTracker.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "question_progress_tracker_id")
    private QuestionProgressTracker questionProgressTracker;

    private LocalDate reviewDate;

    @Convert(converter = DurationToLongConverter.class)
    private Duration questionTime;

    private boolean correct;

    private int userRating;

    public QuestionReview() {}

    public QuestionReview(Long id, UserProgressTracker userProgressTracker, QuestionProgressTracker questionProgressTracker, LocalDate reviewDate, Duration questionTime, boolean correct, int userRating) {
        this.id = id;
        this.userProgressTracker = userProgressTracker;
        this.questionProgressTracker = questionProgressTracker;
        this.reviewDate = reviewDate;
        this.questionTime = questionTime;
        this.correct = correct;
        this.userRating = userRating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuestionProgressTracker getQuestionProgressTracker() {
        return questionProgressTracker;
    }

    public void setQuestionProgressTracker(QuestionProgressTracker questionProgressTracker) {
        this.questionProgressTracker = questionProgressTracker;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }

    public Duration getQuestionTime() {
        return questionTime;
    }

    public void setQuestionTime(Duration questionTime) {
        this.questionTime = questionTime;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public int getUserRating() {
        return userRating;
    }

    public void setUserRating(int userRating) {
        this.userRating = userRating;
    }

    public UserProgressTracker getUserProgressTracker() {
        return userProgressTracker;
    }

    public void setUserProgressTracker(UserProgressTracker userProgressTracker) {
        this.userProgressTracker = userProgressTracker;
    }
}
