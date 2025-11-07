package com.example.mynt_finance_backend.learningPlatformTests.repositories;

import com.example.mynt_finance_backend.learningPlatform.domain.entities.CourseEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.LearningPathEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.UserEntity;
import com.example.mynt_finance_backend.learningPlatform.repositories.UserRepository;
import com.example.mynt_finance_backend.learningPlatformTests.testIntermediaries.TestContainerConfiguration;
import com.example.mynt_finance_backend.learningPlatformTests.testIntermediaries.TestEntityRepositoryFacade;
import com.example.mynt_finance_backend.learningPlatformTests.testUtils.CourseTestUtil;
import com.example.mynt_finance_backend.learningPlatformTests.testUtils.LearningPathTestUtil;
import com.example.mynt_finance_backend.learningPlatformTests.testUtils.UserTestUtil;
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
public class UserRepositoryIntegrationTests implements TestContainerConfiguration {

    @Autowired
    private UserRepository testRepository;

    @Autowired
    private TestEntityRepositoryFacade testEntityRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testUserContainsLearningPath() {

        UserEntity user = UserTestUtil.createEmptyUser();
        testEntityRepository.saveUser(user);
        entityManager.flush();

        LearningPathEntity learningPath = LearningPathTestUtil.createEmptyLearningPath();
        testEntityRepository.saveLearningPath(learningPath);
        entityManager.flush();

        boolean userExists = testRepository.existsById(1L);
        boolean noLearningPath = testRepository.userContainsLearningPath(1L ,1);
        assertThat(userExists).isTrue();
        assertThat(noLearningPath).isFalse();

        user.addLearningPath(learningPath);
        testEntityRepository.saveUser(user);
        entityManager.flush();
        entityManager.clear();

        boolean hasLearningPath = testRepository.userContainsLearningPath(1L, 1);
        UserEntity savedUser = testEntityRepository.findUserById(1L);
        LearningPathEntity savedLearningPath = testEntityRepository.findLearningPathById(1);

        assertThat(savedUser).isNotNull();
        assertThat(savedLearningPath).isNotNull();
        assertThat(hasLearningPath).isTrue();
        assertThat(savedUser.getLearningPaths()).contains(savedLearningPath);
    }

    @Test
    public void testUserContainsCourse() {

        UserEntity user = UserTestUtil.createEmptyUser();
        testEntityRepository.saveUser(user);
        entityManager.flush();

        CourseEntity course = CourseTestUtil.createEmptyCourse();
        testEntityRepository.saveCourse(course);
        entityManager.flush();

        boolean userExists = testRepository.existsById(1L);
        boolean noCourse = testRepository.userContainsCourse(1L ,1);
        assertThat(userExists).isTrue();
        assertThat(noCourse).isFalse();

        user.addCourse(course);
        testEntityRepository.saveUser(user);
        entityManager.flush();
        entityManager.clear();

        boolean hasCourse = testRepository.userContainsCourse(1L, 1);
        UserEntity savedUser = testEntityRepository.findUserById(1L);
        CourseEntity savedCourse = testEntityRepository.findCourseById(1);

        assertThat(savedUser).isNotNull();
        assertThat(savedCourse).isNotNull();
        assertThat(hasCourse).isTrue();
        assertThat(savedUser.getCourses()).contains(savedCourse);
    }
}
