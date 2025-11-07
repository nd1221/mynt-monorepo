package com.example.mynt_finance_backend.learningPlatform.services;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.SectionDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.SectionEntity;
import com.example.mynt_finance_backend.util.baseInterfaces.Service;

import java.util.List;
import java.util.Set;

public interface SectionService extends Service<Integer> {

    boolean hasTest(Integer sectionId);

    boolean hasLesson(Integer sectionId, Integer lessonId);

    Integer getTestId(Integer sectionId);

    // CREATE
    SectionDTO createSection(SectionDTO sectionDTO);

    // READ
    SectionDTO readSectionById(Integer id);

    List<SectionDTO> readAllSections();

    Set<SectionEntity> findAllSectionsById(Set<Integer> sectionIds);

    SectionEntity findSectionById(Integer id);

    // UPDATE
    SectionDTO updateSection(SectionDTO sectionDTO, Integer id);

    SectionDTO addLesson(Integer sectionId, Integer lessonId);

    SectionDTO removeLesson(Integer sectionId, Integer lessonId);

    SectionDTO deleteTest(Integer sectionId);

    // DELETE
    void deleteSection(Integer id);
}
