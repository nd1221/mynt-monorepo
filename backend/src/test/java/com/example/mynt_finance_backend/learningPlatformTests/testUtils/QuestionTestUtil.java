package com.example.mynt_finance_backend.learningPlatformTests.testUtils;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.MultipleChoiceQuestionDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.TrueFalseQuestionDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.MultipleChoiceQuestionEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.QuestionEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.TrueFalseQuestionEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entityEnums.QuestionType;

import java.util.Optional;
import java.util.Set;

public final class QuestionTestUtil {

    private QuestionTestUtil() {}

    public static TrueFalseQuestionEntity createEmptyTrueFalseQuestion() {

        TrueFalseQuestionEntity trueFalseQuestion = new TrueFalseQuestionEntity();
        trueFalseQuestion.setQuestionText("questionText");
        trueFalseQuestion.setQuestionType(QuestionType.TRUE_FALSE);
        trueFalseQuestion.setCorrect(true);
        return trueFalseQuestion;
    }

    public static TrueFalseQuestionEntity createFullTrueFalseQuestion() {

        TrueFalseQuestionEntity trueFalseQuestion = new TrueFalseQuestionEntity();
        trueFalseQuestion.setId(1L);
        trueFalseQuestion.setQuestionText("questionText");
        trueFalseQuestion.setQuestionType(QuestionType.TRUE_FALSE);
        trueFalseQuestion.setLesson(LessonTestUtil.createLessonWithId());
        trueFalseQuestion.setCorrect(true);
        return trueFalseQuestion;
    }

    public static Set<TrueFalseQuestionEntity> createEmptyTrueFalseQuestionSet() {

        TrueFalseQuestionEntity trueFalseQuestion1 = createEmptyTrueFalseQuestion();
        TrueFalseQuestionEntity trueFalseQuestion2 = createEmptyTrueFalseQuestion();
        trueFalseQuestion2.setQuestionText("questionText2");
        TrueFalseQuestionEntity trueFalseQuestion3 = createEmptyTrueFalseQuestion();
        trueFalseQuestion3.setQuestionText("questionText3");

        return Set.of(
                trueFalseQuestion1,
                trueFalseQuestion2,
                trueFalseQuestion3
        );
    }

    public static MultipleChoiceQuestionEntity createEmptyMultipleChoiceQuestion() {

        MultipleChoiceQuestionEntity multipleChoiceQuestion = new MultipleChoiceQuestionEntity();
        multipleChoiceQuestion.setQuestionText("questionText");
        multipleChoiceQuestion.setQuestionType(QuestionType.MULTIPLE_CHOICE);
        return multipleChoiceQuestion;
    }

    public static MultipleChoiceQuestionEntity createFullMultipleChoiceQuestion() {

        MultipleChoiceQuestionEntity multipleChoiceQuestion = new MultipleChoiceQuestionEntity();
        multipleChoiceQuestion.setId(1L);
        multipleChoiceQuestion.setQuestionText("questionText");
        multipleChoiceQuestion.setQuestionType(QuestionType.MULTIPLE_CHOICE);
        multipleChoiceQuestion.setLesson(LessonTestUtil.createLessonWithId());
        multipleChoiceQuestion.setChoices(ChoiceTestUtil.createChoiceEntitySetWithIds());
        return multipleChoiceQuestion;
    }

    public static Set<MultipleChoiceQuestionEntity> createEmptyMultipleChoiceQuestionSet() {

        MultipleChoiceQuestionEntity multipleChoiceQuestion1 = createEmptyMultipleChoiceQuestion();
        MultipleChoiceQuestionEntity multipleChoiceQuestion2 = createEmptyMultipleChoiceQuestion();
        multipleChoiceQuestion2.setQuestionText("questionText2");
        MultipleChoiceQuestionEntity multipleChoiceQuestion3 = createEmptyMultipleChoiceQuestion();
        multipleChoiceQuestion3.setQuestionText("questionText3");

        return Set.of(
                multipleChoiceQuestion1,
                multipleChoiceQuestion2,
                multipleChoiceQuestion3
        );
    }

    public static QuestionEntity createQuestionWithId() {
        QuestionEntity question = new QuestionEntity();
        question.setId(1L);
        return question;
    }

    public static Set<QuestionEntity> createQuestionSet() {

        MultipleChoiceQuestionEntity question1 = new MultipleChoiceQuestionEntity();
        question1.setQuestionText("questionText1");
        question1.setQuestionType(QuestionType.MULTIPLE_CHOICE);
        question1.setChoices(ChoiceTestUtil.createEmptyChoiceSet());
        MultipleChoiceQuestionEntity question2 = new MultipleChoiceQuestionEntity();
        question2.setQuestionText("questionText2");
        question2.setQuestionType(QuestionType.MULTIPLE_CHOICE);
        question2.setChoices(ChoiceTestUtil.createEmptyChoiceSet());
        TrueFalseQuestionEntity question3 = new TrueFalseQuestionEntity();
        question3.setQuestionText("questionText3");
        question3.setQuestionType(QuestionType.TRUE_FALSE);
        question3.setCorrect(true);
        TrueFalseQuestionEntity question4 = new TrueFalseQuestionEntity();
        question4.setQuestionText("questionText4");
        question4.setQuestionType(QuestionType.TRUE_FALSE);
        question4.setCorrect(false);

        return CommonTestUtils.toSet(
                question1,
                question2,
                question3,
                question4
        );
    }

    public static Set<QuestionEntity> createQuestionSetWithIds() {

        MultipleChoiceQuestionEntity question1 = new MultipleChoiceQuestionEntity();
        question1.setId(1L);
        MultipleChoiceQuestionEntity question2 = new MultipleChoiceQuestionEntity();
        question1.setId(2L);
        TrueFalseQuestionEntity question3 = new TrueFalseQuestionEntity();
        question3.setId(3L);
        TrueFalseQuestionEntity question4 = new TrueFalseQuestionEntity();
        question3.setId(4L);

        return Set.of(
                question1,
                question2,
                question3,
                question4
        );
    }

    public static TrueFalseQuestionDTO createEmptyTrueFalseQuestionDTO() {
        return new TrueFalseQuestionDTO(
                null,
                "questionText",
                QuestionType.TRUE_FALSE,
                null,
                3,
                false,
                10,
                2,
                true,
                true
        );
    }

    public static TrueFalseQuestionDTO createFullTrueFalseQuestionDTO() {
        return new TrueFalseQuestionDTO(
                6L,
                "questionText",
                QuestionType.TRUE_FALSE,
                Optional.of(1),
                3,
                false,
                10,
                2,
                true,
                true
        );
    }

    public static MultipleChoiceQuestionDTO createEmptyMultipleChoiceQuestionDTO() {
        return new MultipleChoiceQuestionDTO(
                null,
                "questionText",
                QuestionType.MULTIPLE_CHOICE,
                null,
                9,
                true,
                10,
                3,
                true,
                null
        );
    }

    public static MultipleChoiceQuestionDTO createFullMultipleChoiceQuestionDTO() {
        return new MultipleChoiceQuestionDTO(
                8L,
                "questionText",
                QuestionType.MULTIPLE_CHOICE,
                Optional.of(1),
                12,
                true,
                11,
                4,
                false,
                ChoiceTestUtil.createFullChoiceDTOSet()
        );
    }
}
