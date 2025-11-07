package com.example.mynt_finance_backend.learningPlatformTests.testUtils;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.LessonDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.LessonEntity;

import java.util.Optional;
import java.util.Set;

public final class LessonTestUtil {

    private LessonTestUtil() {}

    public static LessonEntity createEmptyLesson() {
        LessonEntity lesson = new LessonEntity();
        lesson.setTitle("title");
        lesson.setDescription("description");
        lesson.setPosition(1);
        lesson.setContent("content");
        return lesson;
    }

    public static LessonEntity createConvertedLesson() {
        LessonEntity lesson = new LessonEntity();
        lesson.setTitle("title");
        lesson.setDescription("description");
        lesson.setPosition(1);
        lesson.setContent("content");
        lesson.setSection(SectionTestUtil.createEmptySection());
        lesson.setQuestions(QuestionTestUtil.createQuestionSet());
        return lesson;
    }

    public static LessonEntity createFullLesson() {
        LessonEntity lesson = new LessonEntity();
        lesson.setId(1);
        lesson.setTitle("title");
        lesson.setDescription("description");
        lesson.setPosition(1);
        lesson.setContent("content");
        lesson.setSection(SectionTestUtil.createSectionWithId());
        lesson.setQuestions(QuestionTestUtil.createQuestionSetWithIds());
        return lesson;
    }

    public static LessonEntity createLessonWithId() {
        LessonEntity lesson = new LessonEntity();
        lesson.setId(1);
        return lesson;
    }

    public static Set<LessonEntity> createLessonSet() {

        LessonEntity lesson1 = new LessonEntity();
        lesson1.setTitle("title1");
        lesson1.setDescription("description1");
        lesson1.setPosition(1);
        lesson1.setContent("content1");
        LessonEntity lesson2 = new LessonEntity();
        lesson2.setTitle("title2");
        lesson2.setDescription("description2");
        lesson2.setPosition(2);
        lesson2.setContent("content2");
        LessonEntity lesson3 = new LessonEntity();
        lesson3.setTitle("title3");
        lesson3.setDescription("description3");
        lesson3.setPosition(3);
        lesson3.setContent("content3");

        return CommonTestUtils.toSet(
                lesson1,
                lesson2,
                lesson3
        );
    }

    public static Set<LessonEntity> createLessonSetWithIds() {

        LessonEntity lesson1 = new LessonEntity();
        lesson1.setId(1);
        LessonEntity lesson2 = new LessonEntity();
        lesson1.setId(2);
        LessonEntity lesson3 = new LessonEntity();
        lesson1.setId(3);

        return Set.of(
                lesson1,
                lesson2,
                lesson3
        );
    }

    public static LessonDTO createEmptyLessonDTO() {
        return new LessonDTO(
                null,
                "title",
                "description",
                1,
                null,
                1,
                null,
                1000,
                12,
                30,
                15
        );
    }

    public static LessonDTO createFullLessonDTO() {
        return new LessonDTO(
                1,
                "title",
                "description",
                1,
                null,
                1,
                Set.of(1L, 2L, 3L),
                1000,
                24,
                40,
                10
        );
    }
}
