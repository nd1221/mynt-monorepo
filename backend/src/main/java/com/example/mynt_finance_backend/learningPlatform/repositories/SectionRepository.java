package com.example.mynt_finance_backend.learningPlatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.SectionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends JpaRepository<SectionEntity, Integer> {

    @Query("""
            SELECT
                CASE
                    WHEN section.test IS NULL
                    THEN false
                    ELSE true
                END
            FROM SectionEntity section
            WHERE section.id = :sectionId
            """)
    boolean testPresentInSection(@Param("sectionId") int sectionId);

    @Query("""
            SELECT section.test.id
            FROM SectionEntity section
            WHERE section.id = :sectionId
            """)
    Integer getSectionTestId(@Param("sectionId") int sectionId);

    @Query("""
            SELECT exists (
                SELECT 1
                FROM SectionEntity section
                JOIN section.lessons sectionLesson
                WHERE section.id = :sectionId
                AND sectionLesson.id = :lessonId
            )
            """)
    boolean sectionContainsLesson(@Param("sectionId") Integer sectionId, @Param("lessonId") Integer lessonId);
}
