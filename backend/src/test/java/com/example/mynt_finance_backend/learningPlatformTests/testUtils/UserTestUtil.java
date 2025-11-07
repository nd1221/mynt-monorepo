package com.example.mynt_finance_backend.learningPlatformTests.testUtils;

import com.example.mynt_finance_backend.authentication.domain.DTOs.AuthenticationDetailsDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.UserDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.UserEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entityEnums.UserRole;
import com.example.mynt_finance_backend.progressTracking.domain.entities.UserProgressTracker;

import java.time.LocalDateTime;
import java.util.Set;

public final class UserTestUtil {

    private UserTestUtil() {}

    public static UserEntity createEmptyUser() {
        UserEntity user = new UserEntity();
        user.setPassword("password");
        user.setEmail("email");
        user.setRole(UserRole.STUDENT);
        UserProgressTracker upt = new UserProgressTracker();
        upt.setId(1L);
        user.setUserProgressTracker(upt);
        return user;
    }

    public static AuthenticationDetailsDTO createAuthDTO() {
        return new AuthenticationDetailsDTO(
                "user@mail.com",
                "12345"
        );
    }

    public static UserEntity createFullUser() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setPassword("password");
        user.setEmail("email");
        user.setRole(UserRole.STUDENT);
        LearningPathTestUtil.createLearningPathSetWithIds().forEach(user::addLearningPath);
        CourseTestUtil.createCourseSetWithIds().forEach(user::addCourse);
        return user;
    }

    public static Set<UserEntity> createUserSet() {

        UserEntity user1 = new UserEntity();
        user1.setPassword("password1");
        user1.setEmail("email1");
        user1.setRole(UserRole.STUDENT);

        UserEntity user2 = new UserEntity();
        user2.setPassword("password2");
        user2.setEmail("email2");
        user2.setRole(UserRole.STUDENT);

        UserEntity user3 = new UserEntity();
        user3.setPassword("password3");
        user3.setEmail("email3");
        user3.setRole(UserRole.STUDENT);

        return CommonTestUtils.toSet(
                user1,
                user2,
                user3
        );
    }

    public static Set<UserEntity> createEmptyUserSet() {

        UserEntity user1 = createEmptyUser();
        UserEntity user2 = createEmptyUser();
        UserEntity user3 = createEmptyUser();

        return CommonTestUtils.toSet(
                user1,
                user2,
                user3
        );
    }

    public static UserEntity createUserWithId() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        return user;
    }

    public static Set<UserEntity> createUserSetWithIds() {

        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        UserEntity user2 = new UserEntity();
        user2.setId(2L);
        UserEntity user3 = new UserEntity();
        user3.setId(3L);

        return CommonTestUtils.toSet(
                user1,
                user2,
                user3
        );
    }

    public static UserDTO createEmptyUserDTO() {
        return new UserDTO(
                null,
                "password",
                "user@mail.com",
                UserRole.STUDENT,
                null,
                null,
                null
        );
    }

    public static UserDTO createFullUserDTO() {
        return new UserDTO(
                1L,
                "password",
                "user@mail.com",
                UserRole.STUDENT,
                CommonTestUtils.getIntegerIds(),
                CommonTestUtils.getIntegerIds(),
                LocalDateTime.of(2025, 1, 1, 0, 0)
        );
    }
}
