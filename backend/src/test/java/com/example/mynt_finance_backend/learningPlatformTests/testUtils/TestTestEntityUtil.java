package com.example.mynt_finance_backend.learningPlatformTests.testUtils;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.TestDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.TestEntity;

import java.util.Optional;
import java.util.Set;

public final class TestTestEntityUtil {

    private TestTestEntityUtil() {}

    public static TestEntity createEmptyTest() {
        TestEntity test = new TestEntity();
        test.setNumberOfQuestions(10);
        return test;
    }

    public static TestEntity createConvertedTest() {
        TestEntity test = new TestEntity();
        test.setSection(SectionTestUtil.createEmptySection());
        test.setNumberOfQuestions(10);
        test.setTimeLimit(1000L);
        test.setQuestions(QuestionTestUtil.createQuestionSet());
        return test;
    }

    public static TestEntity createFullTest() {
        TestEntity test = new TestEntity();
        test.setId(1);
        test.setNumberOfQuestions(10);
        test.setTimeLimit(1000L);
        test.setQuestions(QuestionTestUtil.createQuestionSetWithIds());
        return test;
    }

    public static TestEntity createTestWithId() {
        TestEntity test = new TestEntity();
        test.setId(1);
        return test;
    }

    public static Set<TestEntity> createTestEntitySetWithIds() {

        TestEntity test1 = new TestEntity();
        test1.setId(1);
        TestEntity test2 = new TestEntity();
        test1.setId(2);
        TestEntity test3 = new TestEntity();
        test1.setId(3);

        return Set.of(
                test1,
                test2,
                test3
        );
    }

    public static Set<TestEntity> createEmptyTestSet() {

        TestEntity test1 = createEmptyTest();
        TestEntity test2 = createEmptyTest();
        test2.setNumberOfQuestions(2);
        TestEntity test3 = createEmptyTest();
        test3.setNumberOfQuestions(3);

        return Set.of(
                test1,
                test2,
                test3
        );
    }

    public static TestDTO createEmptyTestDTO() {
        return new TestDTO(
                null,
                Optional.of(1),
                100,
                1000L,
                null
        );
    }

    public static TestDTO createFullTestDTO() {
        return new TestDTO(
                2,
                Optional.of(5),
                100,
                1000L,
                CommonTestUtils.getLongIds()
        );
    }
}