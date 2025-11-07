package com.example.mynt_finance_backend.learningPlatform.domain.entities;

import com.example.mynt_finance_backend.learningPlatform.domain.entityEnums.QuestionType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@DiscriminatorValue(QuestionType.Constants.TRUE_FALSE_VALUE)
public class TrueFalseQuestionEntity extends QuestionEntity {

    private Boolean isCorrect;

    public TrueFalseQuestionEntity() {}

    public TrueFalseQuestionEntity(Long id, String questionText, QuestionType questionType, LessonEntity lesson, int lessonNumber, boolean core, Boolean isCorrect) {
        super(id, questionText, questionType, lesson, lessonNumber, core);
        this.isCorrect = isCorrect;
    }

    public Boolean getCorrect() {
        return isCorrect;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
    }
}
