package com.example.mynt_finance_backend.learningPlatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.TestEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<TestEntity, Integer> {

    @Query("""
            SELECT exists (
                SELECT 1
                FROM TestEntity test
                JOIN test.questions test_question
                WHERE test.id = :testId
                AND test_question.id = :questionId
            )
            """)
    boolean testContainsQuestion(@Param("testId") Integer testId, @Param("questionId") Long questionId);
}
