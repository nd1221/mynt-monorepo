package com.example.mynt_finance_backend.learningPlatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.TrueFalseQuestionEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface TrueFalseQuestionRepository extends JpaRepository<TrueFalseQuestionEntity, Long> {
}
