package com.example.mynt_finance_backend.learningPlatformTests.testUtils;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.TagDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.TagEntity;

import java.util.Set;

public final class TagTestUtil {

    private TagTestUtil() {}

    public static TagEntity createEmptyTag() {
        TagEntity tag = new TagEntity();
        tag.setTag("tag");
        return tag;
    }

    public static TagEntity createFullTag() {
        TagEntity tag = new TagEntity();
        tag.setId(1);
        tag.setTag("tag");
        tag.setLearningPaths(LearningPathTestUtil.createLearningPathSetWithIds());
        tag.setCourses(CourseTestUtil.createCourseSetWithIds());
        return tag;
    }

    public static Set<TagEntity> createTagSet() {

        TagEntity tag1 = new TagEntity();
        tag1.setTag("tag1");

        TagEntity tag2 = new TagEntity();
        tag2.setTag("tag2");

        TagEntity tag3 = new TagEntity();
        tag3.setTag("tag3");

        return CommonTestUtils.toSet(
                tag1,
                tag2,
                tag3
        );
    }

    public static Set<TagEntity> createTagSetWithIds() {

        TagEntity tag1 = new TagEntity();
        tag1.setId(1);
        TagEntity tag2 = new TagEntity();
        tag1.setId(2);
        TagEntity tag3 = new TagEntity();
        tag1.setId(3);

        return CommonTestUtils.toSet(
                tag1,
                tag2,
                tag3
        );
    }

    public static TagDTO createEmptyTagDTO() {
        return new TagDTO(
                null,
                "tag",
                null,
                null
        );
    }

    public static TagDTO createFullTagDTO() {
        return new TagDTO(
                3,
                "tag",
                CommonTestUtils.getIntegerIds(),
                CommonTestUtils.getIntegerIds()
        );
    }
}
