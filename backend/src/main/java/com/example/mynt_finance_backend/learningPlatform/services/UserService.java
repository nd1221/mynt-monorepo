package com.example.mynt_finance_backend.learningPlatform.services;

import com.example.mynt_finance_backend.authentication.domain.DTOs.AuthenticationDetailsDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.UserDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.UserEntity;
import com.example.mynt_finance_backend.util.baseInterfaces.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService extends Service<Long> {

    boolean hasLearningPath(Long userId, Integer learningPathId);

    boolean hasCourse(Long userId, Integer courseId);

    // CREATE
    UserEntity createUser(AuthenticationDetailsDTO authenticationDetails);

    // READ
    UserDTO readUserById(Long id);

    List<UserDTO> readAllUsers();

    Set<UserEntity> findAllUsersById(Set<Long> userIds);

    Optional<UserEntity> findUserByEmail(String email);

    boolean userExistsByEmail(String email);

    // UPDATE
    UserDTO updateUser(UserDTO userDTO, Long id);

    UserDTO addLearningPath(Long userId, Integer learningPathId);

    UserDTO removeLearningPath(Long userId, Integer learningPathId);

    UserDTO addCourse(Long userId, Integer courseId);

    UserDTO removeCourse(Long userId, Integer courseId);

    void deleteLearningPathAssociations(Integer learningPathId);

    void deleteCourseAssociations(Integer courseId);

    // DELETE
    void deleteUser(Long id);
}
