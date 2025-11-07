package com.example.mynt_finance_backend.learningPlatform.services.impl;

import com.example.mynt_finance_backend.authentication.domain.DTOs.AuthenticationDetailsDTO;
import com.example.mynt_finance_backend.errorHandling.exceptions.DataIntegrityViolationException;
import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.UserDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.CourseEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.LearningPathEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.UserEntity;
import com.example.mynt_finance_backend.progressTracking.domain.entities.UserProgressTracker;
import com.example.mynt_finance_backend.progressTracking.services.intermediaries.ProgressTrackingServicesFacade;
import com.example.mynt_finance_backend.util.baseInterfaces.Mapper;
import com.example.mynt_finance_backend.learningPlatform.repositories.UserRepository;
import com.example.mynt_finance_backend.learningPlatform.services.UserService;
import com.example.mynt_finance_backend.learningPlatform.services.intermediaries.LearningPlatformServicesFacade;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final Mapper<UserDTO, UserEntity> userMapper;

    private final PasswordEncoder passwordEncoder;

    private final LearningPlatformServicesFacade learningPlatformServicesFacade;

    private final ProgressTrackingServicesFacade progressTrackingServicesFacade;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, Mapper<UserDTO, UserEntity> userMapper, @Lazy PasswordEncoder passwordEncoder, @Lazy LearningPlatformServicesFacade learningPlatformServicesFacade, @Lazy ProgressTrackingServicesFacade progressTrackingServicesFacade, EntityManager entityManager) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.learningPlatformServicesFacade = learningPlatformServicesFacade;
        this.progressTrackingServicesFacade = progressTrackingServicesFacade;
        this.entityManager = entityManager;
    }

    @Override
    public boolean exists(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public boolean hasLearningPath(Long userId, Integer learningPathId) {
        return userRepository.userContainsLearningPath(userId, learningPathId);
    }

    @Override
    public boolean hasCourse(Long userId, Integer courseId) {
        return userRepository.userContainsCourse(userId, courseId);
    }

    // CREATE
    @Override
    public UserEntity createUser(AuthenticationDetailsDTO authenticationDetails) {

        UserEntity newUser = new UserEntity();
        newUser.setEmail(authenticationDetails.email());
        newUser.setPassword(passwordEncoder.encode(authenticationDetails.password()));
        progressTrackingServicesFacade.createProgressTracker(newUser);
        return userRepository.save(newUser);
    }

    // READ
    @Override
    public UserDTO readUserById(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        return userEntity.map(userMapper::mapToDTO).orElse(null);
    }

    @Override
    public List<UserDTO> readAllUsers() {
        return userRepository.findAll().stream().map(userMapper::mapToDTO).toList();
    }

    @Override
    public Set<UserEntity> findAllUsersById(Set<Long> userIds) {
        return userIds == null ?
                null :
                new HashSet<>(userRepository.findAllById(userIds));
    }

    @Override
    public Optional<UserEntity> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public boolean userExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    // UPDATE
    @Override
    public UserDTO updateUser(UserDTO userDTO, Long id) {

        UserEntity existingEntity = userRepository.findById(id).get(); // Know it's present, already checked exists()

        updateFieldIfNotNull(existingEntity::setPassword, userDTO.password());
        updateFieldIfNotNull(existingEntity::setEmail, userDTO.email());
        updateFieldIfNotNull(existingEntity::setRole, userDTO.role());

        return userMapper.mapToDTO(userRepository.save(existingEntity));
    }

    @Override
    public UserDTO addLearningPath(Long userId, Integer learningPathId) {
        UserEntity user = userRepository.findById(userId).get();
        user = learningPlatformServicesFacade.addLearningPathToUser(user, learningPathId);
        return userMapper.mapToDTO(user);
    }

    @Override
    public UserDTO removeLearningPath(Long userId, Integer learningPathId) {

        if (!exists(userId) && !learningPlatformServicesFacade.learningPathExists(learningPathId)) {
            return null;
        }

        UserEntity user = userRepository.findById(userId).get();
        boolean found = false;

        Iterator<LearningPathEntity> iterator = user.getLearningPaths().iterator();
        while (iterator.hasNext()) {
            LearningPathEntity learningPath = iterator.next();
            if (Objects.equals(learningPath.getId(), learningPathId)) {
                found = true;
                iterator.remove();
                learningPath.unEnrollUser();
            }
        }

        return found ? userMapper.mapToDTO(user) : null;
    }

    @Override
    public UserDTO addCourse(Long userId, Integer courseId) {

        if (exists(userId) && learningPlatformServicesFacade.courseExists(courseId)) {
            UserEntity user = userRepository.findById(userId).get();
            user = learningPlatformServicesFacade.addCourseToUser(user, courseId);
            progressTrackingServicesFacade.setCourseTrackerActive(user.getUserProgressTracker(), courseId);
            return userMapper.mapToDTO(user);
        }
        return null;
    }

    @Override
    public UserDTO removeCourse(Long userId, Integer courseId) {

        if (!exists(userId) && !learningPlatformServicesFacade.courseExists(courseId)) {
            return null;
        }

        UserEntity user = userRepository.findById(userId).get();
        boolean found = false;

        Iterator<CourseEntity> iterator = user.getCourses().iterator();
        while(iterator.hasNext()) {
            CourseEntity course = iterator.next();
            if (Objects.equals(course.getId(), courseId)) {
                found = true;

                // Check if course is within learning path user is enrolled on
                // If so, block removal
                if (!learningPlatformServicesFacade.allowCourseRemoval(user, courseId)) {
                    throw new DataIntegrityViolationException("You are currently enrolled on a learning path which contains this course");
                }

                iterator.remove();
                course.unEnrollUser();
                progressTrackingServicesFacade.setCourseTrackerInactive(user.getUserProgressTracker(), course);
            }
        }

        progressTrackingServicesFacade.updateMostRecentCourse(user.getUserProgressTracker().getId());

        return found ? userMapper.mapToDTO(user) : null;
    }

    @Override
    public void deleteLearningPathAssociations(Integer learningPathId) {
        userRepository.deleteLearningPathAssociations(learningPathId);
    }

    @Override
    public void deleteCourseAssociations(Integer courseId) {
        userRepository.deleteCourseAssociations(courseId);
    }

    // DELETE
    @Override
    public void deleteUser(Long id) {
        UserEntity user = userRepository.findById(id).get();
        user.getLearningPaths().forEach(LearningPathEntity::unEnrollUser);
        user.getCourses().forEach(CourseEntity::unEnrollUser);
        userRepository.deleteById(id);
    }
}
