package com.example.mynt_finance_backend.learningPlatformTests.repositories;

import com.example.mynt_finance_backend.learningPlatform.domain.entities.CourseEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.LearningPathEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.TagEntity;
import com.example.mynt_finance_backend.learningPlatform.repositories.LearningPathRepository;
import com.example.mynt_finance_backend.learningPlatformTests.testIntermediaries.TestContainerConfiguration;
import com.example.mynt_finance_backend.learningPlatformTests.testIntermediaries.TestEntityRepositoryFacade;
import com.example.mynt_finance_backend.learningPlatformTests.testUtils.CourseTestUtil;
import com.example.mynt_finance_backend.learningPlatformTests.testUtils.LearningPathTestUtil;
import com.example.mynt_finance_backend.learningPlatformTests.testUtils.TagTestUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LearningPathRepositoryIntegrationTests implements TestContainerConfiguration {

    @Autowired
    private LearningPathRepository testRepository;

    @Autowired
    private TestEntityRepositoryFacade testEntityRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testLearningPathContainsCourse() {

        LearningPathEntity learningPath = LearningPathTestUtil.createEmptyLearningPath();
        testEntityRepository.saveLearningPath(learningPath);
        entityManager.flush();

        CourseEntity course = CourseTestUtil.createEmptyCourse();
        testEntityRepository.saveCourse(course);
        entityManager.flush();

        boolean learningPathExists = testRepository.existsById(1);
        boolean noCourse = testRepository.learningPathContainsCourse(1, 1);
        assertThat(learningPathExists).isTrue();
        assertThat(noCourse).isFalse();

        learningPath.addCourse(course);
        testEntityRepository.saveLearningPath(learningPath);
        entityManager.flush();
        entityManager.clear();

        boolean courseExists = testRepository.learningPathContainsCourse(1, 1);
        LearningPathEntity savedLearningPath = testEntityRepository.findLearningPathById(1);
        CourseEntity savedCourse = testEntityRepository.findCourseById(1);

        assertThat(savedLearningPath).isNotNull();
        assertThat(savedCourse).isNotNull();
        assertThat(courseExists).isTrue();
        assertThat(savedLearningPath.getCourses()).contains(savedCourse);
    }

    @Test
    public void testLearningPathContainsTag() {

        LearningPathEntity learningPath = LearningPathTestUtil.createEmptyLearningPath();
        testEntityRepository.saveLearningPath(learningPath);
        entityManager.flush();

        TagEntity tag = TagTestUtil.createEmptyTag();
        testEntityRepository.saveTag(tag);
        entityManager.flush();

        boolean learningPathExists = testRepository.existsById(1);
        boolean noTag = testRepository.learningPathContainsTag(1, 1);
        assertThat(learningPathExists).isTrue();
        assertThat(noTag).isFalse();

        learningPath.addTag(tag);
        tag.addLearningPath(learningPath);
        testEntityRepository.saveLearningPath(learningPath);
        testEntityRepository.saveTag(tag);
        entityManager.flush();
        entityManager.clear();

        boolean tagExists = testRepository.learningPathContainsTag(1, 1);
        LearningPathEntity savedLearningPath = testEntityRepository.findLearningPathById(1);
        TagEntity savedTag = testEntityRepository.findTagById(1);

        assertThat(savedLearningPath).isNotNull();
        assertThat(savedTag).isNotNull();
        assertThat(tagExists).isTrue();
        assertThat(savedLearningPath.getTags()).contains(savedTag);
        assertThat(savedTag.getLearningPaths()).contains(savedLearningPath);
    }
}
