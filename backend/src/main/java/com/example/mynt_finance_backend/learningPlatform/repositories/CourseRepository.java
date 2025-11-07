package com.example.mynt_finance_backend.learningPlatform.repositories;

import com.example.mynt_finance_backend.learningPlatform.domain.entities.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Integer>, PagingAndSortingRepository<CourseEntity, Integer> {

    @Query("""
            SELECT exists(
                SELECT 1
                FROM CourseEntity course
                JOIN course.sections courseSection
                WHERE course.id = :courseId
                AND courseSection.id = :sectionId
            )
            """)
    boolean courseContainsSection(@Param("courseId") Integer courseId, @Param("sectionId") Integer sectionId);

    @Query("""
            SELECT exists (
                SELECT 1
                FROM CourseEntity course
                JOIN course.tags courseTag
                WHERE course.id = :courseId
                AND courseTag.id = :tagId
            )
            """)
    boolean courseContainsTag(@Param("courseId") Integer courseId, @Param("tagId") Integer tagId);
}
