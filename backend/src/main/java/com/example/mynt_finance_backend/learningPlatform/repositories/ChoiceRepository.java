package com.example.mynt_finance_backend.learningPlatform.repositories;

import com.example.mynt_finance_backend.learningPlatform.domain.entities.ChoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChoiceRepository extends JpaRepository<ChoiceEntity, Integer> {

    @Query("""
            SELECT choice
            FROM ChoiceEntity choice
            WHERE choice.question.id = :questionId
            """)
    List<ChoiceEntity> findChoicesByQuestionId(@Param("questionId") Long questionId);
}
