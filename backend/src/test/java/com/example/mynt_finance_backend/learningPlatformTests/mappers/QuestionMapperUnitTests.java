//package com.example.mynt_finance_backend.learningPlatformTests.mappers;
//
//import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.MultipleChoiceQuestionDTO;
//import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.TrueFalseQuestionDTO;
//import com.example.mynt_finance_backend.learningPlatform.domain.entities.*;
//import com.example.mynt_finance_backend.learningPlatform.mappers.Impl.MultipleChoiceQuestionMapperImpl;
//import com.example.mynt_finance_backend.learningPlatform.mappers.Impl.QuestionMapperImpl;
//import com.example.mynt_finance_backend.learningPlatform.mappers.Impl.TrueFalseQuestionMapperImpl;
//import com.example.mynt_finance_backend.util.baseInterfaces.Mapper;
//import com.example.mynt_finance_backend.learningPlatform.mappers.QuestionMapper;
//import com.example.mynt_finance_backend.learningPlatformTests.testUtils.QuestionTestUtil;
//import org.junit.jupiter.api.Test;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//public class QuestionMapperUnitTests {
//
//    private final QuestionMapper testQuestionMapper;
//
//    private final Mapper<TrueFalseQuestionDTO, TrueFalseQuestionEntity> testTrueFalseQuestionMapper;
//
//    private final Mapper<MultipleChoiceQuestionDTO, MultipleChoiceQuestionEntity> testMultipleChoiceQuestionMapper;
//
//    public QuestionMapperUnitTests() {
//        this.testTrueFalseQuestionMapper = new TrueFalseQuestionMapperImpl();
//        this.testMultipleChoiceQuestionMapper = new MultipleChoiceQuestionMapperImpl();
//        this.testQuestionMapper = new QuestionMapperImpl(testMultipleChoiceQuestionMapper, testTrueFalseQuestionMapper);
//    }
//
//    @Test
//    public void testMapEmptyTrueFalseQuestionEntityToDTO() {
//        TrueFalseQuestionEntity questionEntity = QuestionTestUtil.createEmptyTrueFalseQuestion();
//        TrueFalseQuestionDTO mappedDTO = testTrueFalseQuestionMapper.mapToDTO(questionEntity);
//        assertThat(mappedDTO.getQuestionText()).isEqualTo(questionEntity.getQuestionText());
//        assertThat(mappedDTO.getQuestionType()).isEqualTo(questionEntity.getQuestionType());
//        assertThat(mappedDTO.getCorrect()).isEqualTo(questionEntity.getCorrect());
//        assertThat(mappedDTO.getId()).isNull();
//        assertThat(mappedDTO.getLessonId()).isEmpty();
//    }
//
//    @Test
//    public void testMapFullTrueFalseQuestionEntityToDTO() {
//        TrueFalseQuestionEntity questionEntity = QuestionTestUtil.createFullTrueFalseQuestion();
//        TrueFalseQuestionDTO mappedDTO = testTrueFalseQuestionMapper.mapToDTO(questionEntity);
//        assertThat(mappedDTO.getId()).isEqualTo(questionEntity.getId());
//        assertThat(mappedDTO.getQuestionText()).isEqualTo(questionEntity.getQuestionText());
//        assertThat(mappedDTO.getQuestionType()).isEqualTo(questionEntity.getQuestionType());
//        assertThat(mappedDTO.getLessonId()).isPresent();
//        assertThat(mappedDTO.getLessonId().get()).isEqualTo(questionEntity.getLesson().getId());
//        assertThat(mappedDTO.getCorrect()).isEqualTo(questionEntity.getCorrect());
//    }
//
//    @Test
//    public void testMapEmptyTrueFalseQuestionDTOToEntity() {
//        TrueFalseQuestionDTO questionDTO = QuestionTestUtil.createEmptyTrueFalseQuestionDTO();
//        TrueFalseQuestionEntity mappedEntity = testTrueFalseQuestionMapper.mapToEntity(questionDTO);
//        assertThat(mappedEntity.getQuestionText()).isEqualTo(questionDTO.getQuestionText());
//        assertThat(mappedEntity.getQuestionType()).isEqualTo(questionDTO.getQuestionType());
//        assertThat(mappedEntity.getCorrect()).isEqualTo(questionDTO.getCorrect());
//        assertThat(mappedEntity.getId()).isNull();
//        assertThat(mappedEntity.getLesson()).isNull();
//    }
//
//    @Test
//    public void testMapFullTrueFalseQuestionDTOToEntity() {
//        TrueFalseQuestionDTO questionDTO = QuestionTestUtil.createFullTrueFalseQuestionDTO();
//        TrueFalseQuestionEntity mappedEntity = testTrueFalseQuestionMapper.mapToEntity(questionDTO);
//        assertThat(mappedEntity.getId()).isNull();
//        assertThat(mappedEntity.getQuestionText()).isEqualTo(questionDTO.getQuestionText());
//        assertThat(mappedEntity.getQuestionType()).isEqualTo(questionDTO.getQuestionType());
//        assertThat(mappedEntity.getLesson()).isNull();
//        assertThat(mappedEntity.getCorrect()).isEqualTo(questionDTO.getCorrect());
//    }
//
//    @Test
//    public void testMapEmptyMultipleChoiceQuestionEntityToDTO() {
//        MultipleChoiceQuestionEntity questionEntity = QuestionTestUtil.createEmptyMultipleChoiceQuestion();
//        MultipleChoiceQuestionDTO mappedDTO = testMultipleChoiceQuestionMapper.mapToDTO(questionEntity);
//        assertThat(mappedDTO.getQuestionText()).isEqualTo(questionEntity.getQuestionText());
//        assertThat(mappedDTO.getQuestionType()).isEqualTo(questionEntity.getQuestionType());
//        assertThat(mappedDTO.getId()).isNull();
//        assertThat(mappedDTO.getLessonId()).isEmpty();
//    }
//
//    @Test
//    public void testMapFullMultipleChoiceQuestionEntityToDTO() {
//        MultipleChoiceQuestionEntity questionEntity = QuestionTestUtil.createFullMultipleChoiceQuestion();
//        MultipleChoiceQuestionDTO mappedDTO = testMultipleChoiceQuestionMapper.mapToDTO(questionEntity);
//        assertThat(mappedDTO.getId()).isEqualTo(questionEntity.getId());
//        assertThat(mappedDTO.getQuestionText()).isEqualTo(questionEntity.getQuestionText());
//        assertThat(mappedDTO.getQuestionType()).isEqualTo(questionEntity.getQuestionType());
//        assertThat(mappedDTO.getLessonId()).isPresent();
//        assertThat(mappedDTO.getLessonId().get()).isEqualTo(questionEntity.getLesson().getId());
//    }
//
//    @Test
//    public void testMapEmptyMultipleChoiceQuestionDTOToEntity() {
//        MultipleChoiceQuestionDTO questionDTO = QuestionTestUtil.createEmptyMultipleChoiceQuestionDTO();
//        MultipleChoiceQuestionEntity mappedEntity = testMultipleChoiceQuestionMapper.mapToEntity(questionDTO);
//        assertThat(mappedEntity.getQuestionText()).isEqualTo(questionDTO.getQuestionText());
//        assertThat(mappedEntity.getQuestionType()).isEqualTo(questionDTO.getQuestionType());
//        assertThat(mappedEntity.getId()).isNull();
//        assertThat(mappedEntity.getLesson()).isNull();
//        assertThat(mappedEntity.getChoices()).isNull();
//    }
//
//    @Test
//    public void testMapFullMultipleChoiceQuestionDTOToEntity() {
//        MultipleChoiceQuestionDTO questionDTO = QuestionTestUtil.createFullMultipleChoiceQuestionDTO();
//        MultipleChoiceQuestionEntity mappedEntity = testMultipleChoiceQuestionMapper.mapToEntity(questionDTO);
//        assertThat(mappedEntity.getId()).isNull();
//        assertThat(mappedEntity.getQuestionText()).isEqualTo(questionDTO.getQuestionText());
//        assertThat(mappedEntity.getQuestionType()).isEqualTo(questionDTO.getQuestionType());
//        assertThat(mappedEntity.getLesson()).isNull();
//        assertThat(mappedEntity.getChoices()).isNull();
//    }
//
//    @Test
//    public void testMapEmptyTrueFalseQuestionEntityToDTOForQuestionMapperInterface() {
//        TrueFalseQuestionEntity questionEntity = QuestionTestUtil.createEmptyTrueFalseQuestion();
//        TrueFalseQuestionDTO mappedDTO = testQuestionMapper.mapToDTO(questionEntity);
//        assertThat(mappedDTO.getQuestionText()).isEqualTo(questionEntity.getQuestionText());
//        assertThat(mappedDTO.getQuestionType()).isEqualTo(questionEntity.getQuestionType());
//        assertThat(mappedDTO.getCorrect()).isEqualTo(questionEntity.getCorrect());
//        assertThat(mappedDTO.getId()).isNull();
//        assertThat(mappedDTO.getLessonId()).isEmpty();
//    }
//
//    @Test
//    public void testMapFullTrueFalseQuestionEntityToDTOForQuestionMapperInterface() {
//        TrueFalseQuestionEntity questionEntity = QuestionTestUtil.createFullTrueFalseQuestion();
//        TrueFalseQuestionDTO mappedDTO = testQuestionMapper.mapToDTO(questionEntity);
//        assertThat(mappedDTO.getId()).isEqualTo(questionEntity.getId());
//        assertThat(mappedDTO.getQuestionText()).isEqualTo(questionEntity.getQuestionText());
//        assertThat(mappedDTO.getQuestionType()).isEqualTo(questionEntity.getQuestionType());
//        assertThat(mappedDTO.getLessonId()).isPresent();
//        assertThat(mappedDTO.getLessonId().get()).isEqualTo(questionEntity.getLesson().getId());
//        assertThat(mappedDTO.getCorrect()).isEqualTo(questionEntity.getCorrect());
//    }
//
//    @Test
//    public void testMapEmptyTrueFalseQuestionDTOToEntityForQuestionMapperInterface() {
//        TrueFalseQuestionDTO questionDTO = QuestionTestUtil.createEmptyTrueFalseQuestionDTO();
//        TrueFalseQuestionEntity mappedEntity = testQuestionMapper.mapToEntity(questionDTO);
//        assertThat(mappedEntity.getQuestionText()).isEqualTo(questionDTO.getQuestionText());
//        assertThat(mappedEntity.getQuestionType()).isEqualTo(questionDTO.getQuestionType());
//        assertThat(mappedEntity.getCorrect()).isEqualTo(questionDTO.getCorrect());
//        assertThat(mappedEntity.getId()).isNull();
//        assertThat(mappedEntity.getLesson()).isNull();
//    }
//
//    @Test
//    public void testMapFullTrueFalseQuestionDTOToEntityForQuestionMapperInterface() {
//        TrueFalseQuestionDTO questionDTO = QuestionTestUtil.createFullTrueFalseQuestionDTO();
//        TrueFalseQuestionEntity mappedEntity = testQuestionMapper.mapToEntity(questionDTO);
//        assertThat(mappedEntity.getId()).isNull();
//        assertThat(mappedEntity.getQuestionText()).isEqualTo(questionDTO.getQuestionText());
//        assertThat(mappedEntity.getQuestionType()).isEqualTo(questionDTO.getQuestionType());
//        assertThat(mappedEntity.getLesson()).isNull();
//        assertThat(mappedEntity.getCorrect()).isEqualTo(questionDTO.getCorrect());
//    }
//
//    @Test
//    public void testMapEmptyMultipleChoiceQuestionEntityToDTOForQuestionMapperInterface() {
//        MultipleChoiceQuestionEntity questionEntity = QuestionTestUtil.createEmptyMultipleChoiceQuestion();
//        MultipleChoiceQuestionDTO mappedDTO = testQuestionMapper.mapToDTO(questionEntity);
//        assertThat(mappedDTO.getQuestionText()).isEqualTo(questionEntity.getQuestionText());
//        assertThat(mappedDTO.getQuestionType()).isEqualTo(questionEntity.getQuestionType());
//        assertThat(mappedDTO.getId()).isNull();
//        assertThat(mappedDTO.getLessonId()).isEmpty();
//    }
//
//    @Test
//    public void testMapFullMultipleChoiceQuestionEntityToDTOForQuestionMapperInterface() {
//        MultipleChoiceQuestionEntity questionEntity = QuestionTestUtil.createFullMultipleChoiceQuestion();
//        MultipleChoiceQuestionDTO mappedDTO = testQuestionMapper.mapToDTO(questionEntity);
//        assertThat(mappedDTO.getId()).isEqualTo(questionEntity.getId());
//        assertThat(mappedDTO.getQuestionText()).isEqualTo(questionEntity.getQuestionText());
//        assertThat(mappedDTO.getQuestionType()).isEqualTo(questionEntity.getQuestionType());
//        assertThat(mappedDTO.getLessonId()).isPresent();
//        assertThat(mappedDTO.getLessonId().get()).isEqualTo(questionEntity.getLesson().getId());
//    }
//
//    @Test
//    public void testMapEmptyMultipleChoiceQuestionDTOToEntityForQuestionMapperInterface() {
//        MultipleChoiceQuestionDTO questionDTO = QuestionTestUtil.createEmptyMultipleChoiceQuestionDTO();
//        MultipleChoiceQuestionEntity mappedEntity = testQuestionMapper.mapToEntity(questionDTO);
//        assertThat(mappedEntity.getQuestionText()).isEqualTo(questionDTO.getQuestionText());
//        assertThat(mappedEntity.getQuestionType()).isEqualTo(questionDTO.getQuestionType());
//        assertThat(mappedEntity.getId()).isNull();
//        assertThat(mappedEntity.getLesson()).isNull();
//        assertThat(mappedEntity.getChoices()).isNull();
//    }
//
//    @Test
//    public void testMapFullMultipleChoiceQuestionDTOToEntityForQuestionMapperInterface() {
//        MultipleChoiceQuestionDTO questionDTO = QuestionTestUtil.createFullMultipleChoiceQuestionDTO();
//        MultipleChoiceQuestionEntity mappedEntity = testQuestionMapper.mapToEntity(questionDTO);
//        assertThat(mappedEntity.getId()).isNull();
//        assertThat(mappedEntity.getQuestionText()).isEqualTo(questionDTO.getQuestionText());
//        assertThat(mappedEntity.getQuestionType()).isEqualTo(questionDTO.getQuestionType());
//        assertThat(mappedEntity.getLesson()).isNull();
//        assertThat(mappedEntity.getChoices()).isNull();
//    }
//}
