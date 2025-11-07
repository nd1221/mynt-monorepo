package com.example.mynt_finance_backend.progressTracking.domain.entities;

import com.example.mynt_finance_backend.learningPlatform.domain.entities.QuestionEntity;
import com.example.mynt_finance_backend.util.customConverters.DurationToLongConverter;
import jakarta.persistence.*;

import java.time.Duration;

@Entity
@Table(
        name = "aggregate_question_trackers",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"question_id"}
        )
)
public class AggregateQuestionTracker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(
            targetEntity = QuestionEntity.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "question_id")
    private QuestionEntity question;

    double accuracy;

    @Convert(converter = DurationToLongConverter.class)
    private Duration averageQuestionTime;

    int numberOfAttempts;

    protected AggregateQuestionTracker() {}

    private AggregateQuestionTracker(QuestionEntity question, double accuracy, Duration averageQuestionTime, int numberOfAttempts) {
        this.question = question;
        this.accuracy = accuracy;
        this.averageQuestionTime = averageQuestionTime;
        this.numberOfAttempts = numberOfAttempts;
    }

    private AggregateQuestionTracker(Long id, QuestionEntity question, double accuracy, Duration averageQuestionTime, int numberOfAttempts) {
        this.id = id;
        this.question = question;
        this.accuracy = accuracy;
        this.averageQuestionTime = averageQuestionTime;
        this.numberOfAttempts = numberOfAttempts;
    }

    // Initialise tracker
    public static AggregateQuestionTracker initialise(QuestionEntity question) {
        return new AggregateQuestionTracker(
                question,
                0,
                Duration.ofMillis(0),
                0
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuestionEntity getQuestion() {
        return question;
    }

    public void setQuestion(QuestionEntity question) {
        this.question = question;
    }

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

    public int getNumberOfAttempts() {
        return numberOfAttempts;
    }

    public void incrementNumberOfAttempts() {
        this.numberOfAttempts++;
    }
}
