package com.example.mynt_finance_backend.learningPlatform.repositories;

import com.example.mynt_finance_backend.learningPlatform.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("""
            SELECT exists (
                SELECT 1
                FROM UserEntity user
                JOIN user.learningPaths userLearningPath
                WHERE user.id = :userId
                AND userLearningPath.id = :learningPathId
            )
            """)
    boolean userContainsLearningPath(@Param("userId") Long userId, @Param("learningPathId") Integer learningPathId);

    @Query("""
            SELECT exists (
                SELECT 1
                FROM UserEntity user
                JOIN user.courses userCourse
                WHERE user.id = :userId
                AND userCourse.id = :courseId
            )
            """)
    boolean userContainsCourse(@Param("userId") Long userId, @Param("courseId") Integer courseId);

    @Query("""
            SELECT user
            FROM UserEntity user
            WHERE user.email = :email
            """)
    Optional<UserEntity> findUserByEmail(@Param("email") String email);

    @Query("""
            SELECT exists (
                SELECT 1
                FROM UserEntity user
                WHERE user.email = :email
            )
            """)
    boolean existsByEmail(@Param("email") String email);

    @Modifying
    @Query(value = """
            DELETE FROM user_learning_paths
            WHERE learning_path_id = :learningPathId
            """,
    nativeQuery = true)
    void deleteLearningPathAssociations(@Param("learningPathId") Integer learningPathId);

    @Modifying
    @Query(value = """
            DELETE FROM user_courses
            WHERE course_id = :courseId
            """,
    nativeQuery = true)
    void deleteCourseAssociations(@Param("courseId") Integer courseId);
}
