package com.example.mynt_finance_backend.learningPlatform.domain.DTOs;

import com.example.mynt_finance_backend.learningPlatform.domain.entityEnums.QuestionType;

import java.util.Optional;

public class QuestionDTO {

    private final Long id;
    private final String questionText;
    private final QuestionType questionType;
    private final Optional<Integer> lessonId;
    private final int lessonNumber;
    private final boolean core;
    private final int lessonPosition;
    private final int sectionPosition;
    private final boolean locked;

    public QuestionDTO(Long id, String questionText, QuestionType questionType, Optional<Integer> lessonId, int lessonNumber, boolean core, int lessonPosition, int sectionPosition, boolean locked) {
        this.id = id;
        this.questionText = questionText;
        this.questionType = questionType;
        this.lessonId = lessonId;
        this.lessonNumber = lessonNumber;
        this.core = core;
        this.lessonPosition = lessonPosition;
        this.sectionPosition = sectionPosition;
        this.locked = locked;
    }

    public Long getId() {
        return id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public Optional<Integer> getLessonId() {
        return lessonId;
    }

    public int getLessonNumber() {
        return lessonNumber;
    }

    public boolean getCore() {
        return core;
    }

    public int getLessonPosition() { return lessonPosition; }

    public int getSectionPosition() { return sectionPosition; }

    public boolean getLocked() { return locked; }
}
