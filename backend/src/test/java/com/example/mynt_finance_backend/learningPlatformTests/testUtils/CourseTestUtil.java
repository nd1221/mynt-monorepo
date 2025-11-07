package com.example.mynt_finance_backend.learningPlatformTests.testUtils;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.CourseDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.CourseEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entityEnums.ContentDifficulty;

import java.time.LocalDateTime;
import java.util.Set;

public final class CourseTestUtil {

    private CourseTestUtil() {}

    public static CourseEntity createEmptyCourse() {
        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setTitle("course");
        courseEntity.setDescription("description");
        courseEntity.setCreators(CommonTestUtils.getCreators());
        return courseEntity;
    }

    public static CourseEntity createFullCourse() {
        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setId(1);
        courseEntity.setTitle("title");
        courseEntity.setDescription("description");
        courseEntity.setDifficulty(ContentDifficulty.INTERMEDIATE);
        courseEntity.setSections(SectionTestUtil.createSectionSetWithIds());
        courseEntity.setTags(TagTestUtil.createTagSetWithIds());
        courseEntity.setCreators(CommonTestUtils.getCreators());
        courseEntity.setIconURL("icon.url");
        courseEntity.setLastUpdatedAt(LocalDateTime.of(2025, 1, 2, 0,0));
        return courseEntity;
    }

    public static CourseEntity createConvertedCourse() {

        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setTitle("title");
        courseEntity.setDescription("description");
        courseEntity.setDifficulty(ContentDifficulty.INTERMEDIATE);
        courseEntity.setSections(SectionTestUtil.createSectionSet());
        courseEntity.setTags(TagTestUtil.createTagSet());
        courseEntity.setCreators(CommonTestUtils.getCreators());
        courseEntity.setIconURL("icon.url");
        courseEntity.setLastUpdatedAt(LocalDateTime.of(2025, 1, 1, 0,0));
        return courseEntity;
    }

    public static CourseEntity createCourseWithId() {
        CourseEntity course = new CourseEntity();
        course.setId(1);
        return course;
    }

    public static Set<CourseEntity> createCourseSet() {

        CourseEntity course1 = createConvertedCourse();
        course1.setTitle("course1");
        course1.setDescription("description1");
        CourseEntity course2 = createConvertedCourse();
        course2.setTitle("course2");
        course2.setDescription("description2");
        CourseEntity course3 = createConvertedCourse();
        course3.setTitle("course3");
        course3.setDescription("description3");

        return CommonTestUtils.toSet(
                course1,
                course2,
                course3
        );
    }

    public static Set<CourseEntity> createCourseSetWithIds() {

        CourseEntity course1 = new CourseEntity();
        course1.setId(1);
        CourseEntity course2 = new CourseEntity();
        course1.setId(2);
        CourseEntity course3 = new CourseEntity();
        course1.setId(3);

        return CommonTestUtils.toSet(
                course1,
                course2,
                course3
        );
    }

    public static CourseDTO createEmptyCourseDTO() {
        return new CourseDTO(
                null,
                "course",
                "description",
                ContentDifficulty.INTERMEDIATE,
                null,
                null,
                CommonTestUtils.getCreators(),
                null,
                "icon.url",
                null,
                null,
                null,
                null,
                1000
        );
    }

    public static CourseDTO createFullCourseDTO() {
        return new CourseDTO(
                1,
                "title",
                "description",
                ContentDifficulty.INTERMEDIATE,
                CommonTestUtils.getIntegerIds(),
                CommonTestUtils.getStringSet(),
                CommonTestUtils.getCreators(),
                100,
                "icon.url",
                LocalDateTime.of(2025, 1, 1, 0,0),
                LocalDateTime.of(2025, 1, 2, 0,0),
                null,
                null,
                1000
        );
    }
}
