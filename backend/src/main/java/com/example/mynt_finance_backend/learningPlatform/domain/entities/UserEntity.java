package com.example.mynt_finance_backend.learningPlatform.domain.entities;

import com.example.mynt_finance_backend.learningPlatform.domain.entityEnums.UserRole;
import com.example.mynt_finance_backend.progressTracking.domain.entities.UserProgressTracker;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            unique = true,
            nullable = false
    )
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @ManyToMany(
            targetEntity = LearningPathEntity.class,
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "user_learning_paths",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "learning_path_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {
                    "user_id",
                    "learning_path_id"
            })
    )
    @Cascade(CascadeType.PERSIST)
    private Set<LearningPathEntity> learningPaths;

    @ManyToMany(
            targetEntity = CourseEntity.class,
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "user_courses",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    @Cascade(CascadeType.PERSIST)
    private Set<CourseEntity> courses;

    @OneToOne(
            targetEntity = UserProgressTracker.class,
            cascade = jakarta.persistence.CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_progress_tracker_id")
    private UserProgressTracker userProgressTracker;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    public UserEntity() {}

    public UserEntity(Long id, String password, String email, UserRole role, Set<LearningPathEntity> learningPaths, Set<CourseEntity> courses, UserProgressTracker userProgressTracker, LocalDateTime createdAt) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.role = role;
        this.learningPaths = learningPaths;
        this.courses = courses;
        this.userProgressTracker = userProgressTracker;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Set<LearningPathEntity> getLearningPaths() {
        return learningPaths;
    }

    public void addLearningPath(LearningPathEntity learningPath) {
        if (this.learningPaths == null) {
            this.learningPaths = new HashSet<>();
        }
        this.learningPaths.add(learningPath);
    }

    public Set<CourseEntity> getCourses() {
        return courses;
    }

    public void addCourse(CourseEntity course) {
        if (this.courses == null) {
            this.courses = new HashSet<>();
        }
        this.courses.add(course);
    }

    public UserProgressTracker getUserProgressTracker() {
        return userProgressTracker;
    }

    public void setUserProgressTracker(UserProgressTracker userProgressManager) {
        if (this.userProgressTracker == null) {
            this.userProgressTracker = new UserProgressTracker();
        }
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void removeLearningPath(LearningPathEntity toRemove) {
        if (this.learningPaths != null) {
            learningPaths.remove(toRemove);
        }
    }

    public void removeCourse(CourseEntity toRemove) {
        courses.remove(toRemove);
    }

    @PrePersist
    public void setCreatedAt() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }
}
