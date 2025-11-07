package com.example.mynt_finance_backend.progressTracking.domain.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "test_reviews")
public class TestReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(
            targetEntity = TestProgressTracker.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "test_progress_tracker_id")
    private TestProgressTracker testProgressTracker;

    private int score;

    private int numberOfQuestionsAttempted;

    private LocalDate dateAttempted;

    private boolean outOfTime;

    private long testTimeMillis;

    public TestReview() {}

    public TestReview(long id, TestProgressTracker testProgressTracker, int score, int numberOfQuestionsAttempted, LocalDate dateAttempted, boolean outOfTime, long testTimeMillis) {
        this.id = id;
        this.testProgressTracker = testProgressTracker;
        this.score = score;
        this.numberOfQuestionsAttempted = numberOfQuestionsAttempted;
        this.dateAttempted = dateAttempted;
        this.outOfTime = outOfTime;
        this.testTimeMillis = testTimeMillis;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TestProgressTracker getTestProgressTracker() {
        return testProgressTracker;
    }

    public void setTestProgressTracker(TestProgressTracker testProgressTracker) {
        this.testProgressTracker = testProgressTracker;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getNumberOfQuestionsAttempted() {
        return numberOfQuestionsAttempted;
    }

    public void setNumberOfQuestionsAttempted(int numberOfQuestionsAttempted) {
        this.numberOfQuestionsAttempted = numberOfQuestionsAttempted;
    }

    public LocalDate getDateAttempted() {
        return dateAttempted;
    }

    public void setDateAttempted(LocalDate dateAttempted) {
        this.dateAttempted = dateAttempted;
    }

    public boolean isOutOfTime() {
        return outOfTime;
    }

    public void setOutOfTime(boolean outOfTime) {
        this.outOfTime = outOfTime;
    }

    public long getTestTimeMillis() {
        return testTimeMillis;
    }

    public void setTestTimeMillis(long testTimeMillis) {
        this.testTimeMillis = testTimeMillis;
    }
}
