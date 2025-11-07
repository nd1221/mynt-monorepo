package com.example.mynt_finance_backend.learningPlatform.domain.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tags")
public class TagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String tag;

    @ManyToMany(
            targetEntity = LearningPathEntity.class,
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "tag_learning_paths",
            joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "learning_path_id")
    )
    private Set<LearningPathEntity> learningPaths;

    @ManyToMany(
            targetEntity = CourseEntity.class,
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "tag_courses",
            joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<CourseEntity> courses;

    public TagEntity() {}

    public TagEntity(Integer id, String tag, Set<LearningPathEntity> learningPaths, Set<CourseEntity> courses) {
        this.id = id;
        this.tag = tag;
        this.learningPaths = learningPaths;
        this.courses = courses;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Set<LearningPathEntity> getLearningPaths() {
        return learningPaths;
    }

    public void setLearningPaths(Set<LearningPathEntity> learningPaths) {
        this.learningPaths = learningPaths;
    }

    public Set<CourseEntity> getCourses() {
        return courses;
    }

    public void setCourses(Set<CourseEntity> courses) {
        this.courses = courses;
    }

    public void addCourse(CourseEntity course) {
        if (this.courses == null) {
            this.courses = new HashSet<>();
        }
        this.courses.add(course);
    }

    public void removeCourse(CourseEntity toRemove) {
        if (this.courses != null) {
            courses.remove(toRemove);
        }
    }

    public void addLearningPath(LearningPathEntity learningPath) {
        if (this.learningPaths == null) {
            this.learningPaths = new HashSet<>();
        }
        this.learningPaths.add(learningPath);
    }

    public void removeLearningPath(LearningPathEntity toRemove) {
        if (this.learningPaths != null) {
            learningPaths.remove(toRemove);
        }
    }
}
