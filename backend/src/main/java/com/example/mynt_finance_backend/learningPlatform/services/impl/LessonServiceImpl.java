package com.example.mynt_finance_backend.learningPlatform.services.impl;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.LessonContentDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.LessonDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.LessonEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.QuestionEntity;
import com.example.mynt_finance_backend.learningPlatform.repositories.LessonRepository;
import com.example.mynt_finance_backend.util.baseInterfaces.Mapper;
import com.example.mynt_finance_backend.learningPlatform.services.LessonService;
import com.example.mynt_finance_backend.learningPlatform.services.intermediaries.LearningPlatformServicesFacade;
import jakarta.transaction.Transactional;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;

    private final Mapper<LessonDTO, LessonEntity> lessonMapper;

    private final LearningPlatformServicesFacade learningPlatformServicesFacade;

    @Autowired
    public LessonServiceImpl(LessonRepository lessonRepository, Mapper<LessonDTO, LessonEntity> lessonMapper, @Lazy LearningPlatformServicesFacade learningPlatformServicesFacade) {
        this.lessonRepository = lessonRepository;
        this.lessonMapper = lessonMapper;
        this.learningPlatformServicesFacade = learningPlatformServicesFacade;
    }

    @Override
    public boolean exists(Integer id) {
        return lessonRepository.existsById(id);
    }

    @Override
    public boolean hasQuestion(Integer lessonId, Long questionId) {
        return lessonRepository.lessonContainsQuestion(lessonId, questionId);
    }

    // CREATE
    @Override
    public LessonDTO createLesson(LessonDTO lessonDTO) {
        LessonEntity lessonEntity = lessonMapper.mapToEntity(lessonDTO);
        return lessonMapper.mapToDTO(lessonRepository.save(lessonEntity));
    }

    // READ
    @Override
    public LessonDTO readLessonById(Integer id) {
        Optional<LessonEntity> lessonEntity = lessonRepository.findById(id);
        return lessonEntity.map(lessonMapper::mapToDTO).orElse(null);
    }

    @Override
    public List<LessonDTO> readAllLessons() {
        return lessonRepository.findAll().stream().map(lessonMapper::mapToDTO).toList();
    }

    @Override
    public Set<LessonEntity> findAllLessonsById(Set<Integer> lessonIds) {
        return lessonIds == null ?
                null :
                new HashSet<>(lessonRepository.findAllById(lessonIds));
    }

    @Override
    public LessonEntity findLessonById(Integer id) {
        return lessonRepository.findById(id).orElse(null);
    }

    @Override
    public LessonContentDTO getLessonContent(Integer id) {

        LessonEntity lesson = lessonRepository.findById(id).get();
        List<Integer> sectionLessonIds = lesson.getSection().getLessons().stream()
                .sorted(Comparator.comparing(LessonEntity::getPosition))
                .map(LessonEntity::getId)
                .toList();

        int lessonIndex = sectionLessonIds.indexOf(id);
        String content = MarkdownHtmlConverter.markdownToHTML(lesson.getContent());
        Integer prevId = lessonIndex == 0 ? null : sectionLessonIds.get(lessonIndex - 1);
        boolean isLastLesson = lessonIndex == sectionLessonIds.size() - 1;
        Integer nextId = isLastLesson ? lesson.getSection().getTest().getId() : sectionLessonIds.get(lessonIndex + 1);
        Long randomQuestionId = getRandomQuestionId(lesson);

        return new LessonContentDTO(
                lessonMapper.mapToDTO(lesson),
                content,
                prevId,
                nextId,
                isLastLesson,
                randomQuestionId
        );
    }

    private long getRandomQuestionId(LessonEntity lesson) {
        List<Long> questionIds = lesson.getQuestions().stream()
                .map(QuestionEntity::getId)
                .toList();
        int randomIndex = (int) Math.floor(Math.random() * questionIds.size());
        return questionIds.isEmpty() ? 0L : questionIds.get(randomIndex);
    }

    @Override
    public int getNumberOfCoreQuestions(int lessonId) {
        return lessonRepository.numberOfCoreQuestions(lessonId);
    }

    // UPDATE
    @Override
    public LessonDTO updateLesson(LessonDTO lessonDTO, Integer id) {

        LessonEntity existingEntity = lessonRepository.findById(id).get();

        updateFieldIfNotNull(existingEntity::setTitle, lessonDTO.title());
        updateFieldIfNotNull(existingEntity::setDescription, lessonDTO.description());
        updateFieldIfNotNull(existingEntity::setPosition, lessonDTO.position());

        return lessonMapper.mapToDTO(lessonRepository.save(existingEntity));
    }

    @Override
    public LessonDTO addQuestion(Integer lessonId, Long questionId) {
        LessonEntity lesson = lessonRepository.findById(lessonId).get();
        lesson = learningPlatformServicesFacade.addQuestionToLesson(lesson, questionId);
        return lessonMapper.mapToDTO(lesson);
    }

    @Override
    public LessonDTO removeQuestion(Integer lessonId, Long questionId) {
        LessonEntity lesson = lessonRepository.findById(lessonId).get();
        lesson = learningPlatformServicesFacade.removeQuestionFromLesson(lesson, questionId);
        return lessonMapper.mapToDTO(lesson);
    }

    // DELETE
    @Override
    public void deleteLesson(Integer id) {
        learningPlatformServicesFacade.deleteLessonChildren(lessonRepository.findById(id).get());
        lessonRepository.deleteById(id);
    }

    // -----------------------------------------------------------------------------------------------------------------

    private static class MarkdownHtmlConverter {
        private static String markdownToHTML(String markdown) {
            Parser parser = Parser.builder().build();
            Node document = parser.parse(markdown);
            HtmlRenderer renderer = HtmlRenderer.builder().build();
            return renderer.render(document);
        }
    }
}
