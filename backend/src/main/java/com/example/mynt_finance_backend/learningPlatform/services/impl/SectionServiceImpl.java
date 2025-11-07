package com.example.mynt_finance_backend.learningPlatform.services.impl;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.SectionDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.SectionEntity;
import com.example.mynt_finance_backend.learningPlatform.repositories.SectionRepository;
import com.example.mynt_finance_backend.util.baseInterfaces.Mapper;
import com.example.mynt_finance_backend.learningPlatform.services.SectionService;
import com.example.mynt_finance_backend.learningPlatform.services.intermediaries.LearningPlatformServicesFacade;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Optional;

@Service
@Transactional
public class SectionServiceImpl implements SectionService {

    private final SectionRepository sectionRepository;

    private final Mapper<SectionDTO, SectionEntity> sectionMapper;

    private final LearningPlatformServicesFacade learningPlatformServicesFacade;

    @Autowired
    public SectionServiceImpl(SectionRepository sectionRepository, Mapper<SectionDTO, SectionEntity> sectionMapper, @Lazy LearningPlatformServicesFacade learningPlatformServicesFacade) {
        this.sectionRepository = sectionRepository;
        this.sectionMapper = sectionMapper;
        this.learningPlatformServicesFacade = learningPlatformServicesFacade;
    }

    @Override
    public boolean exists(Integer id) {
        return sectionRepository.existsById(id);
    }

    @Override
    public boolean hasTest(Integer sectionId) {
        return sectionRepository.testPresentInSection(sectionId);
    }

    @Override
    public boolean hasLesson(Integer sectionId, Integer lessonId) {
        return sectionRepository.sectionContainsLesson(sectionId, lessonId);
    }

    @Override
    public Integer getTestId(Integer sectionId) {
        return sectionRepository.getSectionTestId(sectionId);
    }

    // CREATE
    @Override
    public SectionDTO createSection(SectionDTO sectionDTO) {
        SectionEntity sectionEntity = sectionMapper.mapToEntity(sectionDTO);
        return sectionMapper.mapToDTO(sectionRepository.save(sectionEntity));
    }

    // READ
    @Override
    public SectionDTO readSectionById(Integer id) {
        Optional<SectionEntity> sectionEntity = sectionRepository.findById(id);
        return sectionEntity.map(sectionMapper::mapToDTO).orElse(null);
    }

    @Override
    public List<SectionDTO> readAllSections() {
        return sectionRepository.findAll().stream().map(sectionMapper::mapToDTO).toList();
    }

    @Override
    public Set<SectionEntity> findAllSectionsById(Set<Integer> sectionIds) {
        return sectionIds == null ?
                null :
                new HashSet<>(sectionRepository.findAllById(sectionIds));
    }

    @Override
    public SectionEntity findSectionById(Integer id) {
        return sectionRepository.findById(id).orElse(null);
    }

    // UPDATE
    @Override
    public SectionDTO updateSection(SectionDTO sectionDTO, Integer id) {

        SectionEntity existingEntity = sectionRepository.findById(id).get();

        updateFieldIfNotNull(existingEntity::setTitle, sectionDTO.title());
        updateFieldIfNotNull(existingEntity::setDescription, sectionDTO.description());
        updateFieldIfNotNull(existingEntity::setPosition, sectionDTO.position());

        return sectionMapper.mapToDTO(sectionRepository.save(existingEntity));
    }

    @Override
    public SectionDTO addLesson(Integer sectionId, Integer lessonId) {
        SectionEntity section = sectionRepository.findById(sectionId).get();
        section = learningPlatformServicesFacade.addLessonToSection(section, lessonId);
        return sectionMapper.mapToDTO(section);
    }

    @Override
    public SectionDTO removeLesson(Integer sectionId, Integer lessonId) {
        SectionEntity section = sectionRepository.findById(sectionId).get();
        section = learningPlatformServicesFacade.removeLessonFromSection(section, lessonId);
        return sectionMapper.mapToDTO(section);
    }

    @Override
    public SectionDTO deleteTest(Integer id) {
        SectionEntity section = sectionRepository.findById(id).get();
        learningPlatformServicesFacade.deleteSectionTest(section);
        return sectionMapper.mapToDTO(section);
    }

    // DELETE
    @Override
    public void deleteSection(Integer id) {
        learningPlatformServicesFacade.deleteSectionChildren(sectionRepository.findById(id).get());
        sectionRepository.deleteById(id);
    }
}
