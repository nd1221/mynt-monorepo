package com.example.mynt_finance_backend.learningPlatformTests.repositories;

import com.example.mynt_finance_backend.learningPlatform.domain.entities.LessonEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.SectionEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.TestEntity;
import com.example.mynt_finance_backend.learningPlatform.repositories.SectionRepository;
import com.example.mynt_finance_backend.learningPlatformTests.testIntermediaries.TestContainerConfiguration;
import com.example.mynt_finance_backend.learningPlatformTests.testIntermediaries.TestEntityRepositoryFacade;
import com.example.mynt_finance_backend.learningPlatformTests.testUtils.LessonTestUtil;
import com.example.mynt_finance_backend.learningPlatformTests.testUtils.SectionTestUtil;
import com.example.mynt_finance_backend.learningPlatformTests.testUtils.TestTestEntityUtil;
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
public class SectionRepositoryIntegrationTests implements TestContainerConfiguration {

    @Autowired
    private SectionRepository testRepository;

    @Autowired
    private TestEntityRepositoryFacade testEntityRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testTestPresentInSection() {

        TestEntity test = TestTestEntityUtil.createEmptyTest();
        testEntityRepository.saveTest(test);
        entityManager.flush();

        SectionEntity section = SectionTestUtil.createEmptySection();
        testEntityRepository.saveSection(section);
        entityManager.flush();

        boolean hasNone = testRepository.testPresentInSection(1);
        assertThat(hasNone).isFalse();

        section.setTest(test);
        test.setSection(section);
        testEntityRepository.saveSection(section);
        testEntityRepository.saveTest(test);
        entityManager.flush();

        SectionEntity savedSection = testEntityRepository.findSectionById(1);
        boolean hasTest = testRepository.testPresentInSection(1);

        assertThat(hasTest).isTrue();
        assertThat(savedSection.getTest()).isNotNull();
    }

    @Test
    public void testGetSectionTestId() {

        TestEntity test = TestTestEntityUtil.createEmptyTest();
        testEntityRepository.saveTest(test);
        entityManager.flush();

        SectionEntity section = SectionTestUtil.createEmptySection();
        testEntityRepository.saveSection(section);
        entityManager.flush();

        Integer noTestId = testRepository.getSectionTestId(1);
        assertThat(noTestId).isNull();

        section.setTest(test);
        test.setSection(section);
        testEntityRepository.saveSection(section);
        testEntityRepository.saveTest(test);
        entityManager.flush();

        SectionEntity savedSection = testEntityRepository.findSectionById(1);
        Integer foundTestId = testRepository.getSectionTestId(1);

        assertThat(foundTestId).isNotNull();
        assertThat(foundTestId).isEqualTo(1);
        assertThat(savedSection).isNotNull();
        assertThat(savedSection.getTest()).isNotNull();
        assertThat(savedSection.getTest().getId()).isEqualTo(foundTestId);
    }

    @Test
    public void testSectionContainsLesson() {

        SectionEntity section = SectionTestUtil.createEmptySection();
        testEntityRepository.saveSection(section);
        entityManager.flush();

        LessonEntity lesson = LessonTestUtil.createEmptyLesson();
        testEntityRepository.saveLesson(lesson);
        entityManager.flush();

        boolean sectionExists = testRepository.existsById(1);
        boolean noLesson = testRepository.sectionContainsLesson(1, 1);
        assertThat(sectionExists).isTrue();
        assertThat(noLesson).isFalse();

        section.addLesson(lesson);
        lesson.setSection(section);
        testEntityRepository.saveSection(section);
        testEntityRepository.saveLesson(lesson);
        entityManager.flush();
        entityManager.clear();

        boolean hasLesson = testRepository.sectionContainsLesson(1, 1);
        SectionEntity savedSection = testEntityRepository.findSectionById(1);
        LessonEntity savedLesson = testEntityRepository.findLessonById(1);

        assertThat(savedSection).isNotNull();
        assertThat(savedLesson).isNotNull();
        assertThat(hasLesson).isTrue();
        assertThat(savedSection.getLessons()).contains(savedLesson);
        assertThat(savedLesson.getSection()).isEqualTo(savedSection);
    }
}
