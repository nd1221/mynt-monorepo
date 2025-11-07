package com.example.mynt_finance_backend.learningPlatformTests.services.impl;

import com.example.mynt_finance_backend.authentication.domain.DTOs.AuthenticationDetailsDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.UserDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.CourseEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.LearningPathEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.UserEntity;
import com.example.mynt_finance_backend.progressTracking.domain.entities.UserProgressTracker;
import com.example.mynt_finance_backend.learningPlatform.domain.entityEnums.UserRole;
import com.example.mynt_finance_backend.learningPlatform.services.UserService;
import com.example.mynt_finance_backend.learningPlatformTests.testIntermediaries.TestContainerConfiguration;
import com.example.mynt_finance_backend.learningPlatformTests.testIntermediaries.TestEntityRepositoryFacade;
import com.example.mynt_finance_backend.learningPlatformTests.testUtils.CourseTestUtil;
import com.example.mynt_finance_backend.learningPlatformTests.testUtils.LearningPathTestUtil;
import com.example.mynt_finance_backend.learningPlatformTests.testUtils.UserTestUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserServiceIntegrationTests implements TestContainerConfiguration {

    @Autowired
    private UserService testService;

    @Autowired
    private TestEntityRepositoryFacade testEntityRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testExists() {

        UserEntity user = UserTestUtil.createEmptyUser();
        testEntityRepository.saveUser(user);
        entityManager.flush();

        boolean exists = testService.exists(1L);
        assertThat(exists).isTrue();
    }

    @Test
    public void testCreateUser() {

        AuthenticationDetailsDTO dto = UserTestUtil.createAuthDTO();
        UserEntity createdUser = testService.createUser(dto);
        entityManager.flush();
        entityManager.clear();

        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getId()).isEqualTo(1L);
        assertThat(createdUser.getPassword()).isNotEqualTo(dto.password()); // should be encrypted
        assertThat(createdUser.getEmail()).isEqualTo(dto.email());
        assertThat(createdUser.getCreatedAt().truncatedTo(ChronoUnit.SECONDS)).isNotNull();
        assertThat(createdUser.getCourses()).isNull();
        assertThat(createdUser.getLearningPaths()).isNull();
        assertThat(createdUser.getUserProgressTracker()).isNotNull();
    }

    @Test
    public void testReadUserById() {

        UserEntity user = UserTestUtil.createEmptyUser();
        testEntityRepository.saveUser(user);
        entityManager.flush();

        UserDTO savedDTO = testService.readUserById(1L);
        assertThat(savedDTO.id()).isEqualTo(1L);
        UserDTO unsavedDTO = testService.readUserById(2L);
        assertThat(unsavedDTO).isNull();
    }

    @Test
    public void testReadAllUsers() {

        List<UserDTO> noUsers = testService.readAllUsers();
        assertThat(noUsers).isNotNull();
        assertThat(noUsers).hasSize(0);

        Set<UserEntity> users = UserTestUtil.createUserSet();
        testEntityRepository.saveUsers(users);
        entityManager.flush();

        List<UserDTO> savedUsers = testService.readAllUsers();

        assertThat(savedUsers).isNotNull();
        assertThat(savedUsers).hasSize(3);
    }

    @Test
    public void testFindAllUsersById() {

        Set<UserEntity> users = UserTestUtil.createUserSet();
        testEntityRepository.saveUsers(users);
        entityManager.flush();

        Set<UserEntity> foundUsers = testService.findAllUsersById(Set.of(1L, 3L));

        assertThat(foundUsers).isNotNull();
        assertThat(foundUsers).hasSize(2);
        foundUsers.forEach(user -> assertThat(user.getId() != 2).isTrue());
    }

    @Test
    public void testUpdateUser() {

        UserEntity user = UserTestUtil.createEmptyUser();
        testEntityRepository.saveUser(user);
        entityManager.flush();

        UserDTO dto = new UserDTO(
                2L,
                "newPassword", //should update
                "newEmail@mail.com", //should update
                UserRole.TEAM_LEADER,
                Set.of(1, 2, 3),
                Set.of(1, 2, 3),
                LocalDateTime.of(2025, 1, 1, 1, 1, 1)
        );

        UserDTO updatedDTO = testService.updateUser(dto, 1L);
        UserEntity savedEntity = testEntityRepository.findUserById(1L);

        assertThat(updatedDTO).isNotNull();
        assertThat(updatedDTO.password()).isEqualTo("newPassword");
        assertThat(updatedDTO.email()).isEqualTo("newEmail@mail.com");
        assertThat(updatedDTO.role()).isEqualTo(UserRole.TEAM_LEADER);
        assertThat(updatedDTO.learningPathIds()).isEmpty();
        assertThat(updatedDTO.courseIds()).isEmpty();
        assertThat(updatedDTO.createdAt()).isNotEqualTo(LocalDateTime.of(2025, 1, 1, 1, 1, 1));
        assertThat(updatedDTO.id()).isEqualTo(savedEntity.getId());
        assertThat(updatedDTO.password()).isEqualTo(savedEntity.getPassword());
        assertThat(updatedDTO.email()).isEqualTo(savedEntity.getEmail());
        assertThat(updatedDTO.role()).isEqualTo(savedEntity.getRole());
        assertThat(updatedDTO.createdAt().truncatedTo(ChronoUnit.SECONDS)).isEqualTo(savedEntity.getCreatedAt().truncatedTo(ChronoUnit.SECONDS));
        assertThat(savedEntity.getLearningPaths()).isNull();
        assertThat(savedEntity.getCourses()).isNull();
    }

    @Test
    public void testAddLearningPath() {

        LearningPathEntity learningPath1 = LearningPathTestUtil.createEmptyLearningPath();
        testEntityRepository.saveLearningPath(learningPath1);
        entityManager.flush();

        UserEntity user = UserTestUtil.createEmptyUser();
        testEntityRepository.saveUser(user);
        entityManager.flush();

        UserDTO dto = testService.addLearningPath(1L, 1);

        assertThat(dto.id()).isEqualTo(user.getId());
        assertThat(dto.password()).isEqualTo(user.getPassword());
        assertThat(dto.email()).isEqualTo(user.getEmail());
        assertThat(dto.role()).isEqualTo(user.getRole());
        assertThat(dto.createdAt().truncatedTo(ChronoUnit.SECONDS)).isEqualTo(user.getCreatedAt().truncatedTo(ChronoUnit.SECONDS));
        assertThat(dto.courseIds()).isEmpty();
        assertThat(dto.learningPathIds()).isNotEmpty();
        assertThat(dto.learningPathIds()).hasSize(1);
        assertThat(dto.learningPathIds()).contains(1);
        assertThat(user.getLearningPaths()).isNotNull();
        assertThat(user.getLearningPaths()).hasSize(1);
        assertThat(user.getLearningPaths()).contains(learningPath1);

        LearningPathEntity learningPath2 = LearningPathTestUtil.createEmptyLearningPath();
        learningPath2.setTitle("learningPath2");
        testEntityRepository.saveLearningPath(learningPath2);
        entityManager.flush();

        UserDTO dto2 = testService.addLearningPath(1L, 2);

        assertThat(dto2.id()).isEqualTo(user.getId());
        assertThat(dto2.password()).isEqualTo(user.getPassword());
        assertThat(dto2.email()).isEqualTo(user.getEmail());
        assertThat(dto2.role()).isEqualTo(user.getRole());
        assertThat(dto2.createdAt().truncatedTo(ChronoUnit.SECONDS)).isEqualTo(user.getCreatedAt().truncatedTo(ChronoUnit.SECONDS));
        assertThat(dto2.courseIds()).isEmpty();
        assertThat(dto2.learningPathIds()).isNotEmpty();
        assertThat(dto2.learningPathIds()).hasSize(2);
        assertThat(dto2.learningPathIds()).contains(1);
        assertThat(dto2.learningPathIds()).contains(2);
        assertThat(user.getLearningPaths()).isNotNull();
        assertThat(user.getLearningPaths()).hasSize(2);
        assertThat(user.getLearningPaths()).contains(learningPath1);
        assertThat(user.getLearningPaths()).contains(learningPath2);
    }

    @Test
    public void testRemoveLearningPath() {

        LearningPathEntity learningPath = LearningPathTestUtil.createEmptyLearningPath();
        testEntityRepository.saveLearningPath(learningPath);
        entityManager.flush();

        UserEntity user = UserTestUtil.createEmptyUser();
        testEntityRepository.saveUser(user);
        entityManager.flush();

        testService.addLearningPath(1L, 1);
        entityManager.flush();

        UserDTO dto = testService.removeLearningPath(1L, 1);
        entityManager.flush();
        entityManager.clear();

        UserEntity userEntity = testEntityRepository.findUserById(1L);
        LearningPathEntity foundLearningPath = testEntityRepository.findLearningPathById(1);

        assertThat(dto.id()).isEqualTo(userEntity.getId());
        assertThat(dto.password()).isEqualTo(userEntity.getPassword());
        assertThat(dto.email()).isEqualTo(userEntity.getEmail());
        assertThat(dto.role()).isEqualTo(userEntity.getRole());
        assertThat(dto.createdAt().truncatedTo(ChronoUnit.SECONDS)).isEqualTo(userEntity.getCreatedAt().truncatedTo(ChronoUnit.SECONDS));
        assertThat(dto.courseIds()).isEmpty();
        assertThat(dto.learningPathIds()).isEmpty();
        assertThat(userEntity.getLearningPaths()).isEmpty();
        assertThat(foundLearningPath).isNotNull();
    }

//    @Test
//    public void testAddCourse() {
//
//        CourseEntity course1 = CourseTestUtil.createEmptyCourse();
//        testEntityRepository.saveCourse(course1);
//        entityManager.flush();
//
//        UserEntity user = UserTestUtil.createEmptyUser();
//        testEntityRepository.saveUser(user);
//        entityManager.flush();
//
//        UserDTO dto = testService.addCourse(1L, 1);
//
//        assertThat(dto.id()).isEqualTo(user.getId());
//        assertThat(dto.password()).isEqualTo(user.getPassword());
//        assertThat(dto.email()).isEqualTo(user.getEmail());
//        assertThat(dto.role()).isEqualTo(user.getRole());
//        assertThat(dto.createdAt().truncatedTo(ChronoUnit.SECONDS)).isEqualTo(user.getCreatedAt().truncatedTo(ChronoUnit.SECONDS));
//        assertThat(dto.learningPathIds()).isEmpty();
//        assertThat(dto.courseIds()).isNotEmpty();
//        assertThat(dto.courseIds()).hasSize(1);
//        assertThat(dto.courseIds()).contains(1);
//        assertThat(user.getCourses()).isNotNull();
//        assertThat(user.getCourses()).hasSize(1);
//        assertThat(user.getCourses()).contains(course1);
//
//        CourseEntity course2 = CourseTestUtil.createEmptyCourse();
//        course2.setTitle("course2");
//        testEntityRepository.saveCourse(course2);
//        entityManager.flush();
//
//        UserDTO dto2 = testService.addCourse(1L, 2);
//
//        assertThat(dto2.id()).isEqualTo(user.getId());
//        assertThat(dto2.password()).isEqualTo(user.getPassword());
//        assertThat(dto2.email()).isEqualTo(user.getEmail());
//        assertThat(dto2.role()).isEqualTo(user.getRole());
//        assertThat(dto2.createdAt().truncatedTo(ChronoUnit.SECONDS)).isEqualTo(user.getCreatedAt().truncatedTo(ChronoUnit.SECONDS));
//        assertThat(dto2.learningPathIds()).isEmpty();
//        assertThat(dto2.courseIds()).isNotEmpty();
//        assertThat(dto2.courseIds()).hasSize(2);
//        assertThat(dto2.courseIds()).contains(1);
//        assertThat(dto2.courseIds()).contains(2);
//        assertThat(user.getCourses()).isNotNull();
//        assertThat(user.getCourses()).hasSize(2);
//        assertThat(user.getCourses()).contains(course1);
//        assertThat(user.getCourses()).contains(course2);
//    }
//
//    @Test
//    public void testRemoveCourse() {
//
//        CourseEntity course = CourseTestUtil.createEmptyCourse();
//        testEntityRepository.saveCourse(course);
//        entityManager.flush();
//
//        UserEntity user = UserTestUtil.createEmptyUser();
//        testEntityRepository.saveUser(user);
//        entityManager.flush();
//
//        testService.addCourse(1L, 1);
//        entityManager.flush();
//
//        UserDTO dto = testService.removeCourse(1L, 1);
//        entityManager.flush();
//        entityManager.clear();
//
//        UserEntity userEntity = testEntityRepository.findUserById(1L);
//        CourseEntity savedCourse = testEntityRepository.findCourseById(1);
//
//        assertThat(dto.id()).isEqualTo(userEntity.getId());
//        assertThat(dto.password()).isEqualTo(userEntity.getPassword());
//        assertThat(dto.email()).isEqualTo(userEntity.getEmail());
//        assertThat(dto.role()).isEqualTo(userEntity.getRole());
//        assertThat(dto.createdAt().truncatedTo(ChronoUnit.SECONDS)).isEqualTo(userEntity.getCreatedAt().truncatedTo(ChronoUnit.SECONDS));
//        assertThat(dto.courseIds()).isEmpty();
//        assertThat(dto.learningPathIds()).isEmpty();
//        assertThat(userEntity.getCourses()).isEmpty();
//        assertThat(savedCourse).isNotNull();
//    }
//
//    @Test
//    public void testDeleteUser() {
//
//        LearningPathEntity learningPath1 = LearningPathTestUtil.createEmptyLearningPath();
//        testEntityRepository.saveLearningPath(learningPath1);
//        entityManager.flush();
//
//        CourseEntity course1 = CourseTestUtil.createEmptyCourse();
//        testEntityRepository.saveCourse(course1);
//        entityManager.flush();
//
//        UserEntity user = UserTestUtil.createEmptyUser();
//        testEntityRepository.saveUser(user);
//        entityManager.flush();
//
//        testService.addLearningPath(1L, 1);
//        testService.addCourse(1L, 1);
//        entityManager.flush();
//
//        testService.deleteUser(1L);
//        entityManager.flush();
//        entityManager.clear();
//
//        UserEntity deletedUser = testEntityRepository.findUserById(1L);
//        LearningPathEntity learningPath = testEntityRepository.findLearningPathById(1);
//        CourseEntity course = testEntityRepository.findCourseById(1);
//
//        assertThat(deletedUser).isNull();
//        assertThat(learningPath).isNotNull();
//        assertThat(course).isNotNull();
//    }

    @Test
    public void testHasLearningPath() {

        LearningPathEntity learningPath = LearningPathTestUtil.createEmptyLearningPath();
        testEntityRepository.saveLearningPath(learningPath);
        entityManager.flush();

        UserEntity user = UserTestUtil.createEmptyUser();
        testEntityRepository.saveUser(user);
        entityManager.flush();

        boolean userExists = testService.exists(1L);
        boolean noLearningPath = testService.hasLearningPath(1L, 1);
        assertThat(userExists).isTrue();
        assertThat(noLearningPath).isFalse();

        user.addLearningPath(learningPath);
        testEntityRepository.saveUser(user);
        entityManager.flush();
        entityManager.clear();

        boolean hasLearningPath1 = testService.hasLearningPath(1L, 1);
        boolean hasLearningPath2 = testService.hasLearningPath(1L, 2);

        assertThat(hasLearningPath1).isTrue();
        assertThat(hasLearningPath2).isFalse();
    }

    @Test
    public void testHasCourse() {

        CourseEntity course = CourseTestUtil.createEmptyCourse();
        testEntityRepository.saveCourse(course);
        entityManager.flush();

        UserEntity user = UserTestUtil.createEmptyUser();
        testEntityRepository.saveUser(user);
        entityManager.flush();

        boolean userExists = testService.exists(1L);
        boolean noCourse = testService.hasCourse(1L, 1);
        assertThat(userExists).isTrue();
        assertThat(noCourse).isFalse();

        user.addCourse(course);
        testEntityRepository.saveUser(user);
        entityManager.flush();
        entityManager.clear();

        boolean hasCourse1 = testService.hasCourse(1L, 1);
        boolean hasCourse2 = testService.hasCourse(1L, 2);

        assertThat(hasCourse1).isTrue();
        assertThat(hasCourse2).isFalse();
    }

    @Test
    public void testDeleteLearningPathAssociations() {

        LearningPathEntity learningPath = LearningPathTestUtil.createEmptyLearningPath();
        testEntityRepository.saveLearningPath(learningPath);
        entityManager.flush();

        Set<UserEntity> users = UserTestUtil.createUserSet();
        testEntityRepository.saveUsers(users);
        entityManager.flush();

        users.forEach(user -> user.addLearningPath(learningPath));
        testEntityRepository.saveUsers(users);
        entityManager.flush();
        entityManager.clear();

        testService.deleteLearningPathAssociations(1);
        LearningPathEntity removedLearningPath = testEntityRepository.findLearningPathById(1);

        Set<UserEntity> savedUsers = testEntityRepository.findAllUsers();
        savedUsers.forEach(user -> assertThat(user.getLearningPaths()).doesNotContain(removedLearningPath));
    }

    @Test
    public void testDeleteCurseAssociations() {

        CourseEntity course = CourseTestUtil.createEmptyCourse();
        testEntityRepository.saveCourse(course);
        entityManager.flush();

        Set<UserEntity> users = UserTestUtil.createUserSet();
        testEntityRepository.saveUsers(users);
        entityManager.flush();

        users.forEach(user -> user.addCourse(course));
        testEntityRepository.saveUsers(users);
        entityManager.flush();
        entityManager.clear();

        testService.deleteCourseAssociations(1);
        CourseEntity removedCourse = testEntityRepository.findCourseById(1);

        Set<UserEntity> savedUsers = testEntityRepository.findAllUsers();
        savedUsers.forEach(user -> assertThat(user.getCourses()).doesNotContain(removedCourse));
    }
}
