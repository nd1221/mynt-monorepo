package com.example.mynt_finance_backend.learningPlatformTests.mappers;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.UserDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.CourseEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.LearningPathEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.UserEntity;
import com.example.mynt_finance_backend.learningPlatform.mappers.Impl.UserMapperImpl;
import com.example.mynt_finance_backend.util.baseInterfaces.Mapper;
import com.example.mynt_finance_backend.learningPlatformTests.testUtils.UserTestUtil;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class UserMapperUnitTests {

    private final Mapper<UserDTO, UserEntity> testMapper;

    public UserMapperUnitTests() {
        this.testMapper = new UserMapperImpl();
    }

    @Test
    public void testMapEmptyUserEntityToDTO() {
        UserEntity userEntity = UserTestUtil.createEmptyUser();
        UserDTO mappedDTO = testMapper.mapToDTO(userEntity);
        assertThat(mappedDTO.password()).isEqualTo(userEntity.getPassword());
        assertThat(mappedDTO.email()).isEqualTo(userEntity.getEmail());
        assertThat(mappedDTO.role()).isEqualTo(userEntity.getRole());
        assertThat(mappedDTO.id()).isNull();
        assertThat(mappedDTO.learningPathIds()).isEmpty();
        assertThat(mappedDTO.courseIds()).isEmpty();
    }

    @Test
    public void testMapEmptyUserDTOToEntity() {
        UserDTO userDTO = UserTestUtil.createEmptyUserDTO();
        UserEntity mappedEntity = testMapper.mapToEntity(userDTO);
        assertThat(mappedEntity.getPassword()).isEqualTo(userDTO.password());
        assertThat(mappedEntity.getEmail()).isEqualTo(userDTO.email());
        assertThat(mappedEntity.getRole()).isEqualTo(userDTO.role());
        assertThat(mappedEntity.getId()).isNull();
        assertThat(mappedEntity.getLearningPaths()).isNull();
        assertThat(mappedEntity.getCourses()).isNull();
        assertThat(mappedEntity.getCreatedAt()).isNull();
    }

    @Test
    public void testMapFullUserEntityToDTO() {
        UserEntity userEntity = UserTestUtil.createFullUser();
        UserDTO mappedDTO = testMapper.mapToDTO(userEntity);
        assertThat(mappedDTO.id()).isEqualTo(userEntity.getId());
        assertThat(mappedDTO.password()).isEqualTo(userEntity.getPassword());
        assertThat(mappedDTO.email()).isEqualTo(userEntity.getEmail());
        assertThat(mappedDTO.role()).isEqualTo(userEntity.getRole());
        assertThat(mappedDTO.learningPathIds()).isEqualTo(userEntity.getLearningPaths().stream().map(LearningPathEntity::getId).collect(Collectors.toSet()));
        assertThat(mappedDTO.courseIds()).isEqualTo(userEntity.getCourses().stream().map(CourseEntity::getId).collect(Collectors.toSet()));
    }

    @Test
    public void testMapFullUserDTOToEntity() {
        UserDTO userDTO = UserTestUtil.createFullUserDTO();
        UserEntity mappedEntity = testMapper.mapToEntity(userDTO);
        assertThat(mappedEntity.getId()).isNull();
        assertThat(mappedEntity.getPassword()).isEqualTo(userDTO.password());
        assertThat(mappedEntity.getEmail()).isEqualTo(userDTO.email());
        assertThat(mappedEntity.getRole()).isEqualTo(userDTO.role());
        assertThat(mappedEntity.getLearningPaths()).isNull();
        assertThat(mappedEntity.getCourses()).isNull();
        assertThat(mappedEntity.getCreatedAt()).isEqualTo(userDTO.createdAt());
    }
}
