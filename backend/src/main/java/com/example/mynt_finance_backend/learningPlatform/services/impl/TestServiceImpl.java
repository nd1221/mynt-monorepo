package com.example.mynt_finance_backend.learningPlatform.services.impl;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.TestDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.TestEntity;
import com.example.mynt_finance_backend.learningPlatform.repositories.TestRepository;
import com.example.mynt_finance_backend.learningPlatform.services.TestService;
import com.example.mynt_finance_backend.util.baseInterfaces.Mapper;
import com.example.mynt_finance_backend.learningPlatform.services.intermediaries.LearningPlatformServicesFacade;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;

    private final Mapper<TestDTO, TestEntity> testMapper;

    private final LearningPlatformServicesFacade learningPlatformServicesFacade;

    @Autowired
    public TestServiceImpl(TestRepository testRepository, Mapper<TestDTO, TestEntity> testMapper, @Lazy LearningPlatformServicesFacade learningPlatformServicesFacade) {
        this.testRepository = testRepository;
        this.testMapper = testMapper;
        this.learningPlatformServicesFacade = learningPlatformServicesFacade;
    }

    @Override
    public boolean exists(Integer id) {
        return testRepository.existsById(id);
    }

    @Override
    public boolean hasQuestion(Integer testId, Long questionId) {
        return testRepository.testContainsQuestion(testId, questionId);
    }

    // CREATE
    @Override
    public TestDTO createTest(Integer sectionId, TestDTO testDTO) {
        TestEntity testEntity = testMapper.mapToEntity(testDTO);
        testEntity = learningPlatformServicesFacade.addTestToSection(sectionId, testEntity);
        return testMapper.mapToDTO(testRepository.save(testEntity));
    }

    // READ
    @Override
    public TestDTO readTest(Integer sectionId) {
        return testMapper.mapToDTO(learningPlatformServicesFacade.getSectionTest(sectionId));
    }

    @Override
    public TestEntity findById(int testId) {
        // Existence guaranteed
        // All usages of this method are only called if the test exists and can be accessed by user in frontend
        return testRepository.findById(testId).get();
    }

    // UPDATE
    @Override
    public TestDTO updateTestBySectionId(Integer sectionId, TestDTO testDTO) {

        TestEntity existingEntity = testRepository.findById(sectionId).get();

        updateFieldIfNotNull(existingEntity::setNumberOfQuestions, testDTO.numberOfQuestions());
        updateFieldIfNotNull(existingEntity::setTimeLimit, testDTO.timeLimit());

        return testMapper.mapToDTO(testRepository.save(existingEntity));
    }

    @Override
    public TestDTO addQuestion(Integer testId, Long questionId) {
        TestEntity test = testRepository.findById(testId).get();
        test = learningPlatformServicesFacade.addQuestionToTest(test, questionId);
        return testMapper.mapToDTO(test);
    }

    @Override
    public TestDTO removeQuestion(Integer testId, Long questionId) {
        TestEntity test = testRepository.findById(testId).get();
        test = learningPlatformServicesFacade.removeQuestionFromTest(test, questionId);
        return testMapper.mapToDTO(test);
    }

    // DELETE
    @Override
    public void deleteTest(Integer sectionId, Integer testId) {
        learningPlatformServicesFacade.removeTestFromSection(sectionId);
        testRepository.deleteById(testId);
    }
}
