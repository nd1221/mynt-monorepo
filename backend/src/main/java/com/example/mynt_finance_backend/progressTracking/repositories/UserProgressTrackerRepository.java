package com.example.mynt_finance_backend.progressTracking.repositories;

import com.example.mynt_finance_backend.progressTracking.domain.entities.UserProgressTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProgressTrackerRepository extends JpaRepository<UserProgressTracker, Long> {

    @Query("""
            SELECT userProgressTracker
            FROM UserProgressTracker userProgressTracker
            WHERE userProgressTracker.user.id = :userId
            """)
    UserProgressTracker findByUserId(@Param("userId") Long userId);
}
