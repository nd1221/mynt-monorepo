package com.example.mynt_finance_backend.learningPlatformTests.repositories;

import com.example.mynt_finance_backend.learningPlatform.domain.entities.CourseEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.SectionEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.TagEntity;
import com.example.mynt_finance_backend.learningPlatform.repositories.CourseRepository;
import com.example.mynt_finance_backend.learningPlatformTests.testIntermediaries.TestContainerConfiguration;
import com.example.mynt_finance_backend.learningPlatformTests.testIntermediaries.TestEntityRepositoryFacade;
import com.example.mynt_finance_backend.learningPlatformTests.testUtils.CourseTestUtil;
import com.example.mynt_finance_backend.learningPlatformTests.testUtils.SectionTestUtil;
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
public class CourseRepositoryIntegrationTests implements TestContainerConfiguration {

    @Autowired
    private CourseRepository testRepository;

    @Autowired
    private TestEntityRepositoryFacade testEntityRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testCourseContainsSection() {

        CourseEntity course = CourseTestUtil.createEmptyCourse();
        testEntityRepository.saveCourse(course);
        entityManager.flush();

        SectionEntity section = SectionTestUtil.createEmptySection();
        testEntityRepository.saveSection(section);
        entityManager.flush();

        boolean courseExists = testRepository.existsById(1);
        boolean noSection = testRepository.courseContainsSection(1, 1);
        assertThat(courseExists).isTrue();
        assertThat(noSection).isFalse();

        course.addSection(section);
        section.setCourse(course);
        testEntityRepository.saveCourse(course);
        testEntityRepository.saveSection(section);
        entityManager.flush();
        entityManager.clear();

        boolean sectionExists = testRepository.courseContainsSection(1, 1);
        CourseEntity savedCourse = testEntityRepository.findCourseById(1);
        SectionEntity savedSection = testEntityRepository.findSectionById(1);

        assertThat(savedCourse).isNotNull();
        assertThat(savedSection).isNotNull();
        assertThat(sectionExists).isTrue();
        assertThat(savedCourse.getSections()).contains(savedSection);
        assertThat(savedSection.getCourse()).isEqualTo(savedCourse);
    }

    @Test
    public void testCourseContainsTag() {

        CourseEntity course = CourseTestUtil.createEmptyCourse();
        testEntityRepository.saveCourse(course);
        entityManager.flush();

        TagEntity tag = TagTestUtil.createEmptyTag();
        testEntityRepository.saveTag(tag);
        entityManager.flush();

        boolean courseExists = testRepository.existsById(1);
        boolean noTag = testRepository.courseContainsTag(1, 1);
        assertThat(courseExists).isTrue();
        assertThat(noTag).isFalse();

        course.addTag(tag);
        tag.addCourse(course);
        testEntityRepository.saveCourse(course);
        testEntityRepository.saveTag(tag);
        entityManager.flush();
        entityManager.clear();

        boolean tagExists = testRepository.courseContainsTag(1, 1);
        CourseEntity savedCourse = testEntityRepository.findCourseById(1);
        TagEntity savedTag = testEntityRepository.findTagById(1);

        assertThat(savedCourse).isNotNull();
        assertThat(savedTag).isNotNull();
        assertThat(tagExists).isTrue();
        assertThat(savedCourse.getTags()).contains(savedTag);
        assertThat(savedTag.getCourses()).contains(savedCourse);
    }
}
