package com.example.mynt_finance_backend.learningPlatformTests.testIntermediaries;

import com.example.mynt_finance_backend.learningPlatform.domain.entities.*;
import com.example.mynt_finance_backend.learningPlatform.repositories.*;
import com.example.mynt_finance_backend.progressTracking.domain.entities.UserProgressTracker;
import com.example.mynt_finance_backend.progressTracking.domain.entities.TestProgressTracker;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Transactional
public class TestEntityRepositoryFacade {

    private final ChoiceRepository choiceRepository;

    private final CourseRepository courseRepository;

    private final LearningPathRepository learningPathRepository;

    private final LessonRepository lessonRepository;

    private final QuestionRepositoryFacade questionRepository;

    private final SectionRepository sectionRepository;

    private final TagRepository tagRepository;

    private final TestRepository testRepository;

    private final UserRepository userRepository;

    @Autowired
    public TestEntityRepositoryFacade(ChoiceRepository choiceRepository, CourseRepository courseRepository, LearningPathRepository learningPathRepository, LessonRepository lessonRepository, QuestionRepositoryFacade questionRepository, SectionRepository sectionRepository, TagRepository tagRepository, TestRepository testRepository, UserRepository userRepository) {
        this.choiceRepository = choiceRepository;
        this.courseRepository = courseRepository;
        this.learningPathRepository = learningPathRepository;
        this.lessonRepository = lessonRepository;
        this.questionRepository = questionRepository;
        this.sectionRepository = sectionRepository;
        this.tagRepository = tagRepository;
        this.testRepository = testRepository;
        this.userRepository = userRepository;
    }

    public void saveChoice(ChoiceEntity choice) {
        choiceRepository.save(choice);
    }

    public void saveChoices(Iterable<ChoiceEntity> choices) {
        choices.forEach(choiceRepository::save);
    }

    public ChoiceEntity findChoiceById(Integer id) {
        return choiceRepository.findById(id).orElse(null);
    }

    public Set<ChoiceEntity> findAllChoices() {
        return new HashSet<>(choiceRepository.findAll());
    }

    public Set<ChoiceEntity> findChoicesByQuestionId(Long id) {
        return new HashSet<>(choiceRepository.findChoicesByQuestionId(id));
    }

    public void saveCourse(CourseEntity course) {
        courseRepository.save(course);
    }

    public void saveCourses(Set<CourseEntity> courses) {
        courses.forEach(courseRepository::save);
    }

    public CourseEntity findCourseById(Integer id) {
        return courseRepository.findById(id).orElse(null);
    }

    public Set<CourseEntity> findAllCourses() {
        return new HashSet<>(courseRepository.findAll());
    }

    public void saveLearningPath(LearningPathEntity learningPath) {
        learningPathRepository.save(learningPath);
    }

    public void saveLearningPaths(Set<LearningPathEntity> learningPaths) {
        learningPaths.forEach(learningPathRepository::save);
    }

    public LearningPathEntity findLearningPathById(Integer id) {
        return learningPathRepository.findById(id).orElse(null);
    }

    public Set<LearningPathEntity> findAllLearningPaths() {
        return new HashSet<>(learningPathRepository.findAll());
    }

    public void saveLesson(LessonEntity lesson) {
        lessonRepository.save(lesson);
    }

    public void saveLessons(Set<LessonEntity> lessons) {
        lessons.forEach(lessonRepository::save);
    }

    public LessonEntity findLessonById(Integer id) {
        return lessonRepository.findById(id).orElse(null);
    }

    public Set<LessonEntity> findAllLessons() {
        return new HashSet<>(lessonRepository.findAll());
    }

    public void saveQuestion(TrueFalseQuestionEntity question) {
        questionRepository.save(question);
    }

    public void saveQuestion(MultipleChoiceQuestionEntity question) {
        questionRepository.save(question);
    }

    public void saveQuestions(Set<QuestionEntity> questions) {
        questions.forEach(question -> {
            if (question instanceof MultipleChoiceQuestionEntity) {
                questionRepository.save((MultipleChoiceQuestionEntity) question);
            }
            if (question instanceof TrueFalseQuestionEntity) {
                questionRepository.save((TrueFalseQuestionEntity) question);
            }
        });
    }

    public QuestionEntity findQuestionById(Long id) {
        return questionRepository.findQuestionById(id).orElse(null);
    }

    public Set<QuestionEntity> findAllQuestions() {
        Set<QuestionEntity> questions = new HashSet<>();
        questions.addAll(questionRepository.findAllTrueFalseQuestions());
        questions.addAll(questionRepository.findAllMultipleChoiceQuestions());
        return questions;
    }

    public void saveSection(SectionEntity section) {
        sectionRepository.save(section);
    }

    public void saveSections(Set<SectionEntity> sections) {
        sections.forEach(sectionRepository::save);
    }

    public SectionEntity findSectionById(Integer id) {
        return sectionRepository.findById(id).orElse(null);
    }

    public Set<SectionEntity> findAllSections() {
        return new HashSet<>(sectionRepository.findAll());
    }

    public void saveTag(TagEntity tag) {
        tagRepository.save(tag);
    }

    public void saveTags(Set<TagEntity> tags) {
        tags.forEach(tagRepository::save);
    }

    public TagEntity findTagById(Integer id) {
        return tagRepository.findById(id).orElse(null);
    }

    public Set<TagEntity> findAllTags() {
        return new HashSet<>(tagRepository.findAll());
    }

    public void saveTest(TestEntity test) {
        testRepository.save(test);
    }

    public void saveTests(Iterable<TestEntity> tests) {
        tests.forEach(testRepository::save);
    }

    public TestEntity findTestById(Integer id) {
        return testRepository.findById(id).orElse(null);
    }

    public void saveUser(UserEntity user) {
        userRepository.save(user);
    }

    public void saveUsers(Set<UserEntity> users) {
        users.forEach(userRepository::save);
    }

    public UserEntity findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public Set<UserEntity> findAllUsers() {
        return new HashSet<>(userRepository.findAll());
    }
}
