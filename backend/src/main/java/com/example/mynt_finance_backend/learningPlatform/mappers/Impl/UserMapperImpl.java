package com.example.mynt_finance_backend.learningPlatform.mappers.Impl;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.UserDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.CourseEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.LearningPathEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.UserEntity;
import com.example.mynt_finance_backend.util.baseInterfaces.Mapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements Mapper<UserDTO, UserEntity> {

    @Override
    public UserDTO mapToDTO(UserEntity userEntity) {

        return new UserDTO(
                userEntity.getId(),
                userEntity.getPassword(),
                userEntity.getEmail(),
                userEntity.getRole(),
                getChildEntitySet(userEntity.getLearningPaths(), LearningPathEntity::getId),
                getChildEntitySet(userEntity.getCourses(), CourseEntity::getId),
                userEntity.getCreatedAt()
        );
    }

    @Override
    public UserEntity mapToEntity(UserDTO userDTO) {
        return new UserEntity(
                null,
                userDTO.password(),
                userDTO.email(),
                userDTO.role(),
                null,
                null,
                null,
                userDTO.createdAt()
        );
    }
}
