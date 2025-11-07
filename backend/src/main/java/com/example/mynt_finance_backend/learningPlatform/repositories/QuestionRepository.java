package com.example.mynt_finance_backend.learningPlatform.repositories;

import com.example.mynt_finance_backend.learningPlatform.domain.entities.QuestionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, Long>, JpaSpecificationExecutor<QuestionEntity> {

    @Query("""
            SELECT count(q)
            FROM QuestionEntity q
            WHERE q.lesson.section.course.id = :courseId
            """)
    Long findNumberOfQuestionsInCourse(@Param("courseId") int courseId);

    @Query("""
            SELECT count(q)
            FROM QuestionEntity q
            WHERE q.lesson.section.id = :sectionId
            """)
    Long findNumberOfQuestionsInSection(@Param("sectionId") int sectionId);

    @Query("""
            SELECT count(q)
            FROM QuestionEntity q
            WHERE q.lesson.id = :lessonId
            """)
    Long findNumberOfQuestionsInLesson(@Param("lessonId") int lessonId);
}
