package com.example.mynt_finance_backend.learningPlatformTests.testUtils;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.LearningPathDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.LearningPathEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entityEnums.ContentDifficulty;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public final class LearningPathTestUtil {

    private LearningPathTestUtil() {}

    public static LearningPathEntity createEmptyLearningPath() {
        LearningPathEntity learningPath = new LearningPathEntity();
        learningPath.setTitle("learningPath");
        learningPath.setDescription("description");
        learningPath.setCreators(CommonTestUtils.getCreators());
        learningPath.setCourses(new HashSet<>());
        return learningPath;
    }

    public static LearningPathEntity createConvertedLearningPath() {
        LearningPathEntity learningPath = new LearningPathEntity();
        learningPath.setTitle("learningPath");
        learningPath.setDescription("description");
        learningPath.setCourses(CourseTestUtil.createCourseSet());
        learningPath.setTags(TagTestUtil.createTagSet());
        learningPath.setCreators(CommonTestUtils.getCreators());
        learningPath.setIconURL("icon.url");
        learningPath.setLastUpdatedAt(LocalDateTime.of(2025, 1, 2, 0, 0));
        return learningPath;
    }

    public static LearningPathEntity createFullLearningPath() {
        LearningPathEntity learningPath = new LearningPathEntity();
        learningPath.setId(1);
        learningPath.setTitle("title");
        learningPath.setDescription("description");
        learningPath.setCourses(CourseTestUtil.createCourseSetWithIds());
        learningPath.setTags(TagTestUtil.createTagSetWithIds());
        learningPath.setCreators(CommonTestUtils.getCreators());
        learningPath.setIconURL("icon.url");
        learningPath.setLastUpdatedAt(LocalDateTime.of(2025, 1, 2, 0, 0));
        return learningPath;
    }

    public static Set<LearningPathEntity> createLearningPathSet() {

        LearningPathEntity learningPath1 = new LearningPathEntity();
        learningPath1.setTitle("learningPath1");
        learningPath1.setDescription("description1");
        learningPath1.setCreators(CommonTestUtils.getCreators());

        LearningPathEntity learningPath2 = new LearningPathEntity();
        learningPath2.setTitle("learningPath2");
        learningPath2.setDescription("description2");
        learningPath2.setCreators(CommonTestUtils.getCreators());

        LearningPathEntity learningPath3 = new LearningPathEntity();
        learningPath3.setTitle("learningPath3");
        learningPath3.setDescription("description3");
        learningPath3.setCreators(CommonTestUtils.getCreators());

        return CommonTestUtils.toSet(
                learningPath1,
                learningPath2,
                learningPath3
        );
    }

    public static Set<LearningPathEntity> createLearningPathSetWithIds() {

        LearningPathEntity learningPath1 = new LearningPathEntity();
        learningPath1.setId(1);
        LearningPathEntity learningPath2 = new LearningPathEntity();
        learningPath1.setId(2);
        LearningPathEntity learningPath3 = new LearningPathEntity();
        learningPath1.setId(3);

        return CommonTestUtils.toSet(
                learningPath1,
                learningPath2,
                learningPath3
        );
    }

    public static LearningPathDTO createEmptyLearningPathDTO() {
        return new LearningPathDTO(
                null,
                "title",
                "description",
                null,
                null,
                null,
                CommonTestUtils.getCreators(),
                null,
                "icon.url",
                null,
                null
        );
    }

    public static LearningPathDTO createFullLearningPathDTO() {
        return new LearningPathDTO(
                1,
                "title",
                "description",
                ContentDifficulty.BEGINNER,
                CommonTestUtils.getIntegerIds(),
                CommonTestUtils.getStringSet(),
                CommonTestUtils.getCreators(),
                100,
                "icon.url",
                LocalDateTime.of(2025, 1, 1, 0, 0),
                LocalDateTime.of(2025, 1, 2, 0, 0)
        );
    }
}