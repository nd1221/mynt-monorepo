package com.example.mynt_finance_backend.learningPlatform.repositories;

import com.example.mynt_finance_backend.learningPlatform.domain.entities.LearningPathEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LearningPathRepository extends JpaRepository<LearningPathEntity, Integer>, PagingAndSortingRepository<LearningPathEntity, Integer> {

    @Query("""
            SELECT exists (
                SELECT 1
                FROM LearningPathEntity learningPath
                JOIN learningPath.courses learningPathCourse
                WHERE learningPath.id = :learningPathId
                AND learningPathCourse.id = :courseId
            )
            """)
    boolean learningPathContainsCourse(@Param("learningPathId") Integer learningPathId, @Param("courseId") Integer courseId);

    @Query("""
            SELECT exists (
                SELECT 1
                FROM LearningPathEntity learningPath
                JOIN learningPath.tags learningPathTag
                WHERE learningPath.id = :learningPathId
                AND learningPathTag.id = :tagId
            )
            """)
    boolean learningPathContainsTag(@Param("learningPathId") Integer learningPathId, @Param("tagId") Integer tagId);
}
