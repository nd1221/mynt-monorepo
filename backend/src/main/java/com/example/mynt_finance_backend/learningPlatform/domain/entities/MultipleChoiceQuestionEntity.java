package com.example.mynt_finance_backend.learningPlatform.domain.entities;

import com.example.mynt_finance_backend.learningPlatform.domain.entityEnums.QuestionType;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue(QuestionType.Constants.MULTIPLE_CHOICE_VALUE)
public class MultipleChoiceQuestionEntity extends QuestionEntity {

    @OneToMany(
            targetEntity = ChoiceEntity.class,
            mappedBy = "question",
            fetch = FetchType.LAZY
    )
    private Set<ChoiceEntity> choices;

    public MultipleChoiceQuestionEntity() {
    }

    public MultipleChoiceQuestionEntity(Long id, String questionText, QuestionType questionType, LessonEntity lesson, int lessonNumber, boolean core, Set<ChoiceEntity> choices) {
        super(id, questionText, questionType, lesson, lessonNumber, core);
        this.choices = choices;
    }

    public Set<ChoiceEntity> getChoices() {
        return choices;
    }

    public void setChoices(Set<ChoiceEntity> choices) {
        this.choices = choices;
    }

    public void addChoice(ChoiceEntity choice) {
        if (this.choices == null) {
            this.choices = new HashSet<>();
        }
        this.choices.add(choice);
    }
}
