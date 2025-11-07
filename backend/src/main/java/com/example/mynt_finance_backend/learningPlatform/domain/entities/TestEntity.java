package com.example.mynt_finance_backend.learningPlatform.domain.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tests")
public class TestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(
            targetEntity = SectionEntity.class,
            mappedBy = "test",
            fetch = FetchType.LAZY
    )
    private SectionEntity section;

    @Column(nullable = false)
    private Integer numberOfQuestions;

    private Long timeLimit;

    @ManyToMany(
            targetEntity = QuestionEntity.class,
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "test_questions",
            joinColumns = @JoinColumn(name = "test_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    private Set<QuestionEntity> questions;

    public TestEntity() {}

    public TestEntity(Integer id, SectionEntity section, Integer numberOfQuestions, Long timeLimit, Set<QuestionEntity> questions) {
        this.id = id;
        this.section = section;
        this.numberOfQuestions = numberOfQuestions;
        this.timeLimit = timeLimit;
        this.questions = questions;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SectionEntity getSection() {
        return section;
    }

    public void setSection(SectionEntity section) {
        this.section = section;
    }

    public Integer getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(Integer numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public Long getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Long timeLimit) {
        this.timeLimit = timeLimit;
    }

    public Set<QuestionEntity> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<QuestionEntity> questions) {
        this.questions = questions;
    }

    public void addQuestion(QuestionEntity question) {
        if (this.questions == null) {
            this.questions = new HashSet<>();
        }
        this.questions.add(question);
    }

    public void removeQuestion(QuestionEntity question) {
        if (this.questions != null) {
            this.questions.remove(question);
        }
    }
}
