package com.example.mynt_finance_backend.learningPlatform.domain.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "choices")
public class ChoiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String choiceText;

    @Column(nullable = false)
    private Boolean isCorrect;

    @ManyToOne(
            targetEntity = QuestionEntity.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "question_id")
    private QuestionEntity question;

    public ChoiceEntity() {}

    public ChoiceEntity(Integer id, String choiceText, Boolean isCorrect, QuestionEntity question) {
        this.id = id;
        this.choiceText = choiceText;
        this.isCorrect = isCorrect;
        this.question = question;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChoiceText() {
        return choiceText;
    }

    public void setChoiceText(String choiceText) {
        this.choiceText = choiceText;
    }

    public Boolean getCorrect() {
        return isCorrect;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
    }

    public QuestionEntity getQuestion() {
        return question;
    }

    public void setQuestion(QuestionEntity question) {
        this.question = question;
    }
}
