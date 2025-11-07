package com.example.mynt_finance_backend.learningPlatform.services.impl;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.ChoiceDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.ChoiceEntity;
import com.example.mynt_finance_backend.learningPlatform.repositories.ChoiceRepository;
import com.example.mynt_finance_backend.learningPlatform.services.ChoiceService;
import com.example.mynt_finance_backend.util.baseInterfaces.Mapper;
import com.example.mynt_finance_backend.learningPlatform.services.intermediaries.LearningPlatformServicesFacade;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ChoiceServiceImpl implements ChoiceService {

    private final ChoiceRepository choiceRepository;

    private final Mapper<ChoiceDTO, ChoiceEntity> choiceMapper;

    private final LearningPlatformServicesFacade learningPlatformServicesFacade;

    @Autowired
    public ChoiceServiceImpl(ChoiceRepository choiceRepository, Mapper<ChoiceDTO, ChoiceEntity> choiceMapper, @Lazy LearningPlatformServicesFacade learningPlatformServicesFacade) {
        this.choiceRepository = choiceRepository;
        this.choiceMapper = choiceMapper;
        this.learningPlatformServicesFacade = learningPlatformServicesFacade;
    }

    @Override
    public boolean exists(Integer id) {
        return choiceRepository.existsById(id);
    }

    // CREATE
    @Override
    public ChoiceDTO createChoice(Long questionId, ChoiceDTO choiceDTO) {
        ChoiceEntity choiceEntity = choiceMapper.mapToEntity(choiceDTO);
        choiceEntity = learningPlatformServicesFacade.addChoiceToQuestion(questionId, choiceEntity);
        return choiceMapper.mapToDTO(choiceRepository.save(choiceEntity));
    }

    // READ
    @Override
    public List<ChoiceDTO> readAllChoices(Long questionId) {
        return choiceRepository.findChoicesByQuestionId(questionId).stream()
                .map(choiceMapper::mapToDTO)
                .toList();
    }

    // UPDATE
    @Override
    public ChoiceDTO updateChoice(Long questionId, Integer choiceId, ChoiceDTO choiceDTO) {

        ChoiceEntity existingEntity = choiceRepository.findById(choiceId).get();

        updateFieldIfNotNull(existingEntity::setChoiceText, choiceDTO.choiceText());
        updateFieldIfNotNull(existingEntity::setCorrect, choiceDTO.isCorrect());

        return choiceMapper.mapToDTO(choiceRepository.save(existingEntity));
    }

    // DELETE
    public void deleteChoiceById(Integer choiceId) {
        choiceRepository.deleteById(choiceId);
    }
}
