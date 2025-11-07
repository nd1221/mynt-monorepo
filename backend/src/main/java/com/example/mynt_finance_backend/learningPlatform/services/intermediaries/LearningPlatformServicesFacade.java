package com.example.mynt_finance_backend.learningPlatform.services.intermediaries;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.*;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.*;
import com.example.mynt_finance_backend.learningPlatform.services.*;
import com.example.mynt_finance_backend.progressTracking.domain.entities.UserProgressTracker;
import com.example.mynt_finance_backend.progressTracking.services.intermediaries.ProgressTrackingServicesFacade;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class LearningPlatformServicesFacade {

    private final ChoiceService choiceService;

    private final UserService userService;

    private final LearningPathService learningPathService;

    private final CourseService courseService;

    private final SectionService sectionService;

    private final LessonService lessonService;

    private final QuestionService questionService;

    private final TagService tagService;

    private final TestService testService;

    private final ProgressTrackingServicesFacade progressTrackingServicesFacade;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public LearningPlatformServicesFacade(ChoiceService choiceService, UserService userService, LearningPathService learningPathService, CourseService courseService, SectionService sectionService, LessonService lessonService, QuestionService questionService, TagService tagService, TestService testService, @Lazy ProgressTrackingServicesFacade progressTrackingServicesFacade) {
        this.choiceService = choiceService;
        this.userService = userService;
        this.learningPathService = learningPathService;
        this.courseService = courseService;
        this.sectionService = sectionService;
        this.lessonService = lessonService;
        this.questionService = questionService;
        this.tagService = tagService;
        this.testService = testService;
        this.progressTrackingServicesFacade = progressTrackingServicesFacade;
    }

    // USERS ===========================================================================================================

    public UserEntity addLearningPathToUser(UserEntity user, int learningPathId) {

        LearningPathEntity learningPath = learningPathService.findLearningPathById(learningPathId).get();
        learningPath.enrollUser();
        user.addLearningPath(learningPath);

        // Add all courses in learning path to user if not already enrolled
        // UserEntity contains Set<CourseEntity> so no duplicates are allowed
        learningPath.getCourses().forEach(user::addCourse);

        return user;
    }

    public UserEntity addCourseToUser(UserEntity user, int courseId) {
        CourseEntity course = courseService.findCourseById(courseId).get();
        course.enrollUser();
        user.addCourse(course);
        return user;
    }

    public boolean allowCourseRemoval(UserEntity user, int courseId) {
        return user.getLearningPaths().stream()
                .flatMap(learningPath ->
                        learningPath.getCourses().stream()
                                .map(CourseEntity::getId)
                )
                .noneMatch(id -> id.equals(courseId));
    }

    // LEARNING PATHS ==================================================================================================

    public boolean learningPathExists(int id) {
        return learningPathService.exists(id);
    }

    public void deleteLearningPathChildren(LearningPathEntity learningPath) {
        if (learningPath.getTags() != null) {
            learningPath.getTags().forEach(tag -> tag.removeLearningPath(learningPath));
        }
    }

    public void removeLearningPathParentAssociations(LearningPathEntity learningPath) {
        userService.deleteLearningPathAssociations(learningPath.getId());
    }

    public void addCourseToLearningPath(LearningPathEntity learningPath, int courseId) {
        CourseEntity course = courseService.findCourseById(courseId).get();
        learningPath.addCourse(course);
    }

    public void removeCourseFromLearningPath(LearningPathEntity learningPath, Integer courseId) {
        CourseEntity course = courseService.findCourseById(courseId).get();
        learningPath.removeCourse(course);
    }

    public void addTagToLearningPath(LearningPathEntity learningPath, Integer tagId) {
        TagEntity tag = tagService.findTagById(tagId);
        learningPath.addTag(tag);
        tag.addLearningPath(learningPath);
    }

    public void removeTagFromLearningPath(LearningPathEntity learningPath, Integer tagId) {
        TagEntity tag = tagService.findTagById(tagId);
        learningPath.removeTag(tag);
        tag.removeLearningPath(learningPath);
    }

    // COURSES =========================================================================================================

    public boolean courseExists(int id) {
        return courseService.exists(id);
    }

    public CourseEntity getCourseById(int courseId) {
        return courseService.findCourseById(courseId).get(); // Check has already occurred in controller
    }

    public void deleteCourseChildren(CourseEntity course) {

        if (course.getTags() != null) {
            course.getTags().forEach(tag -> tag.removeCourse(course));
        }

        Set<SectionEntity> sections = course.getSections();
        if (sections != null && !sections.isEmpty()) {
            Set<Integer> sectionIds = sections.stream().map(SectionEntity::getId).collect(Collectors.toSet());
            sectionIds.forEach(sectionService::deleteSection);
        }
    }

    public void deleteCourseParentAssociations(CourseEntity course) {
        userService.deleteCourseAssociations(course.getId());
    }

    public void addSectionToCourse(CourseEntity course, Integer sectionId) {
        SectionEntity section = sectionService.findSectionById(sectionId);
        section.setCourse(course);
        course.addSection(section);
    }

    public void removeSectionFromCourse(CourseEntity course, Integer sectionId) {
        SectionEntity section = sectionService.findSectionById(sectionId);
        course.removeSection(section);
        sectionService.deleteSection(sectionId);
    }

    public void addTagToCourse(CourseEntity course, Integer tagId) {
        TagEntity tag = tagService.findTagById(tagId);
        course.addTag(tag);
        tag.addCourse(course);
    }

    public void removeTagFromCourse(CourseEntity course, Integer tagId) {
        TagEntity tag = tagService.findTagById(tagId);
        course.removeTag(tag);
        tag.removeCourse(course);
    }

    // TESTS ===========================================================================================================

    public TestEntity getSectionTest(Integer sectionId) {
        return sectionService.findSectionById(sectionId).getTest();
    }

    public TestEntity addQuestionToTest(TestEntity test, Long questionId) {
        QuestionEntity question = questionService.findQuestionById(questionId);
        test.addQuestion(question);
        return test;
    }

    public TestEntity removeQuestionFromTest(TestEntity test, Long questionId) {
        QuestionEntity question = questionService.findQuestionById(questionId);
        test.removeQuestion(question);
        return test;
    }

    public TestEntity findTestById(int testId) {
        return testService.findById(testId);
    }

    // QUESTIONS =======================================================================================================

    public void deleteChoices(Long questionId) {
        List<Integer> choiceIdsToDelete = choiceService.readAllChoices(questionId).stream().map(ChoiceDTO::id).toList();
        choiceIdsToDelete.forEach(choiceService::deleteChoiceById);
    }

    public QuestionDTO mapQuestionToDTO(QuestionEntity question, boolean locked) {
        return questionService.mapQuestion(question, locked);
    }

    public QuestionEntity findQuestionById(long questionId) {
        return questionService.findQuestionById(questionId);
    }

    public Long getNumberOfQuestionsInCourse(int courseId) {
        return questionService.getNumberOfQuestionsInCourse(courseId);
    }

    public Long getNumberOfQuestionsInSection(int sectionId) {
        return questionService.getNumberOfQuestionsInSection(sectionId);
    }

    public Long getNumberOfQuestionsInLesson(int lessonId) {
        return questionService.getNumberOfQuestionsInLesson(lessonId);
    }

    // CHOICES =========================================================================================================

    public ChoiceEntity addChoiceToQuestion(Long questionId, ChoiceEntity choice) {
        MultipleChoiceQuestionEntity question = (MultipleChoiceQuestionEntity) questionService.findQuestionById(questionId);
        question.addChoice(choice);
        choice.setQuestion(question);
        return choice;
    }

    // SECTIONS ========================================================================================================

    public TestEntity addTestToSection(Integer sectionId, TestEntity test) {
        SectionEntity section = sectionService.findSectionById(sectionId);
        section.setTest(test);
        test.setSection(section);
        return test;
    }

    public void removeTestFromSection(Integer sectionId) {
        SectionEntity section = sectionService.findSectionById(sectionId);
        section.removeTest();
    }

    public SectionEntity addLessonToSection(SectionEntity section, Integer lessonId) {
        LessonEntity lesson = lessonService.findLessonById(lessonId);
        section.addLesson(lesson);
        lesson.setSection(section);
        return section;
    }

    public SectionEntity removeLessonFromSection(SectionEntity section, Integer lessonId) {
        LessonEntity lesson = lessonService.findLessonById(lessonId);
        section.removeLesson(lesson);
        lessonService.deleteLesson(lessonId);
        return section;
    }

    public void deleteSectionChildren(SectionEntity section) {

        Set<LessonEntity> lessons = section.getLessons();
        if (lessons != null && !lessons.isEmpty()) {
            Set<Integer> lessonIds = lessons.stream().map(LessonEntity::getId).collect(Collectors.toSet());
            lessonIds.forEach(lessonService::deleteLesson);
        }

        if (section.getTest() != null) {
            deleteSectionTest(section);
        }
    }

    public void deleteSectionTest(SectionEntity section) {
        testService.deleteTest(section.getId(), section.getTest().getId());
    }

    public SectionEntity findSectionById(int sectionId) {
        return sectionService.findSectionById(sectionId);
    }

    // LESSONS =========================================================================================================

    public LessonEntity findLessonById(Integer lessonId) {
        return lessonService.findLessonById(lessonId);
    }

    public LessonEntity addQuestionToLesson(LessonEntity lesson, Long questionId) {
        QuestionEntity question = questionService.findQuestionById(questionId);
        lesson.addQuestion(question);
        question.setLesson(lesson);
        return lesson;
    }

    public LessonEntity removeQuestionFromLesson(LessonEntity lesson, Long questionId) {
        QuestionEntity question = questionService.findQuestionById(questionId);
        lesson.removeQuestion(question);
        questionService.deleteQuestionById(questionId);
        return lesson;
    }

    public void deleteLessonChildren(LessonEntity lesson) {
        if (lesson.getSection() != null) {
            lesson.getSection().removeLesson(lesson);
        }
        if (lesson.getQuestions() != null) {
            lesson.getQuestions().forEach(question -> questionService.deleteQuestionById(question.getId()));
        }
    }

    public LessonDTO getLessonDTOById(int lessonId) {
        return lessonService.readLessonById(lessonId);
    }

    public CourseDTO getCourseDTOById(int courseId) {
        return courseService.readCourseById(courseId);
    }

    public int getNumberOfCoreQuestionsInLesson(int lessonId) {
        return lessonService.getNumberOfCoreQuestions(lessonId);
    }
}