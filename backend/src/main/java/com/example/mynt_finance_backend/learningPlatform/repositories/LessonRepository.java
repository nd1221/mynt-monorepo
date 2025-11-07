package com.example.mynt_finance_backend.learningPlatform.repositories;

import com.example.mynt_finance_backend.learningPlatform.domain.entities.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends JpaRepository<LessonEntity, Integer> {

    @Query("""
            SELECT exists (
                SELECT 1
                FROM LessonEntity lesson
                JOIN lesson.questions lessonQuestion
                WHERE lesson.id = :lessonId
                AND lessonQuestion.id = :questionId
            )
            """)
    boolean lessonContainsQuestion(@Param("lessonId") Integer lessonId, @Param("questionId") Long questionId);

    @Query("""
            SELECT lesson.content
            FROM LessonEntity lesson
            WHERE lesson.id = :lessonId
            """)
    String findContentById(@Param("lessonId") Integer lessonId);

    @Query("""
            SELECT count(q)
            FROM LessonEntity lesson
            JOIN lesson.questions q
            WHERE lesson.id = :lessonId
            AND q.core = TRUE
            """)
    int numberOfCoreQuestions(@Param("lessonId") int lessonId);
}
