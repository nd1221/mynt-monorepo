package com.example.mynt_finance_backend.learningPlatform.domain.entities;

import com.example.mynt_finance_backend.learningPlatform.domain.entityEnums.QuestionType;
import com.example.mynt_finance_backend.progressTracking.domain.entities.AggregateQuestionTracker;
import com.example.mynt_finance_backend.progressTracking.domain.entities.QuestionProgressTracker;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "questions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(nullable = false)
    protected String questionText;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "type",
            insertable = false,
            updatable = false
    )
    protected QuestionType questionType;

    @ManyToOne(
            targetEntity = LessonEntity.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "lesson_id")
    protected LessonEntity lesson;

    int lessonNumber;

    boolean core;

    @OneToOne(
            targetEntity = AggregateQuestionTracker.class,
            mappedBy = "question",
            fetch = FetchType.LAZY
    )
    private AggregateQuestionTracker aggregateQuestionTracker;

    @OneToMany(
            targetEntity = QuestionProgressTracker.class,
            mappedBy = "question",
            fetch = FetchType.LAZY
    )
    private List<QuestionProgressTracker> questionProgressTrackers;

    public QuestionEntity() {};

    public QuestionEntity(Long id, String questionText, QuestionType questionType, LessonEntity lesson, int lessonNumber, boolean core) {
        this.id = id;
        this.questionText = questionText;
        this.questionType = questionType;
        this.lesson = lesson;
        this.lessonNumber = lessonNumber;
        this.core = core;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public LessonEntity getLesson() {
        return lesson;
    }

    public void setLesson(LessonEntity lesson) {
        this.lesson = lesson;
    }

    public int getLessonNumber() {
        return lessonNumber;
    }

    public void setLessonNumber(int lessonNumber) {
        this.lessonNumber = lessonNumber;
    }

    public boolean isCore() {
        return core;
    }

    public void setCore(boolean core) {
        this.core = core;
    }

    public AggregateQuestionTracker getAggregateQuestionTracker() {
        return aggregateQuestionTracker;
    }

    public void setAggregateQuestionTracker(AggregateQuestionTracker aggregateQuestionTracker) {
        this.aggregateQuestionTracker = aggregateQuestionTracker;
    }

    public List<QuestionProgressTracker> getQuestionProgressTrackers() {
        return questionProgressTrackers;
    }

    public void setQuestionProgressTrackers(List<QuestionProgressTracker> questionProgressTrackers) {
        this.questionProgressTrackers = questionProgressTrackers;
    }
}
