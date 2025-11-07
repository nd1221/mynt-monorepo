package com.example.mynt_finance_backend.learningPlatformTests.testUtils;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.SectionDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.SectionEntity;

import java.util.Optional;
import java.util.Set;

public final class SectionTestUtil {

    private SectionTestUtil() {}

    public static SectionEntity createEmptySection() {
        SectionEntity section = new SectionEntity();
        section.setTitle("section");
        section.setDescription("description");
        section.setPosition(1);
        return section;
    }

    public static SectionEntity createConvertedSection() {
        SectionEntity section = new SectionEntity();
        section.setTitle("section");
        section.setDescription("description");
        section.setPosition(1);
        section.setCourse(CourseTestUtil.createEmptyCourse());
        section.setLessons(LessonTestUtil.createLessonSet());
        section.setTest(TestTestEntityUtil.createEmptyTest());
        return section;
    }

    public static SectionEntity createFullSection() {
        SectionEntity section = new SectionEntity();
        section.setId(1);
        section.setTitle("section");
        section.setDescription("description");
        section.setPosition(1);
        section.setCourse(CourseTestUtil.createCourseWithId());
        section.setLessons(LessonTestUtil.createLessonSetWithIds());
        section.setTest(TestTestEntityUtil.createTestWithId());
        return section;
    }

    public static Set<SectionEntity> createSectionSet() {

        SectionEntity section1 = new SectionEntity();
        section1.setTitle("section1");
        section1.setDescription("description1");
        section1.setPosition(1);

        SectionEntity section2 = new SectionEntity();
        section2.setTitle("section2");
        section2.setDescription("description2");
        section2.setPosition(1);

        SectionEntity section3 = new SectionEntity();
        section3.setTitle("section3");
        section3.setDescription("description3");
        section3.setPosition(1);

        return CommonTestUtils.toSet(
                section1,
                section2,
                section3
        );
    }

    public static Set<SectionEntity> createEmptySectionSet() {

        SectionEntity section1 = createEmptySection();
        SectionEntity section2 = createEmptySection();
        section2.setTitle("section2");
        SectionEntity section3 = createEmptySection();
        section3.setTitle("section3");

        return CommonTestUtils.toSet(
                section1,
                section2,
                section3
        );
    }

    public static SectionEntity createSectionWithId() {
        SectionEntity section = new SectionEntity();
        section.setId(1);
        return section;
    }

    public static Set<SectionEntity> createSectionSetWithIds() {

        SectionEntity section1 = new SectionEntity();
        section1.setId(1);
        SectionEntity section2 = new SectionEntity();
        section1.setId(2);
        SectionEntity section3 = new SectionEntity();
        section1.setId(3);

        return CommonTestUtils.toSet(
                section1,
                section2,
                section3
        );
    }

    public static SectionDTO createEmptySectionDTO() {
        return new SectionDTO(
                null,
                "title",
                "description",
                1,
                null,
                null,
                null,
                1000
        );
    }

    public static SectionDTO createFullSectionDTO() {
        return new SectionDTO(
                1,
                "title",
                "description",
                1,
                Optional.of(1),
                CommonTestUtils.getIntegerIds(),
                Optional.of(1),
                1000
        );
    }
}
