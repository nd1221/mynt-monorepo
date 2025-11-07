package com.example.mynt_finance_backend.learningPlatform.mappers.Impl;

public interface QuestionMapper<DTO, Entity> {

    DTO mapToDTO(Entity entity, boolean locked);

    Entity mapToEntity(DTO dto);
}
