package com.example.mynt_finance_backend.learningPlatform.domain.entities;

import com.example.mynt_finance_backend.learningPlatform.domain.entityEnums.ContentDifficulty;
import com.example.mynt_finance_backend.learningPlatform.domain.entityInterfaces.Enrollable;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "learning_paths")
public class LearningPathEntity implements Enrollable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String description;

    @Enumerated(EnumType.STRING)
    private ContentDifficulty difficulty;

    @ManyToMany(
            targetEntity = CourseEntity.class,
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "learning_path_courses",
            joinColumns = @JoinColumn(name = "learning_path_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<CourseEntity> courses;

    @ManyToMany(
            targetEntity = TagEntity.class,
            mappedBy = "learningPaths",
            fetch = FetchType.LAZY
    )
    private Set<TagEntity> tags;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "learning_path_creators",
            joinColumns = @JoinColumn(
                    name = "learning_path_id"
            )
    )
    @Column(name = "creator")
    private Set<String> creators;

    @Column()
    private Integer numberOfEnrolledUsers;

    private String iconURL;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Column()
    private LocalDateTime lastUpdatedAt;

    public LearningPathEntity() {}

    public LearningPathEntity(Integer id, String title, String description, ContentDifficulty difficulty, Set<CourseEntity> courses, Set<TagEntity> tags, Set<String> creators, String iconURL, LocalDateTime lastUpdatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
        this.courses = courses;
        this.tags = tags;
        this.creators = creators;
        setNumberOfEnrolledUsers();
        this.iconURL = iconURL;
        setCreatedAt();
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ContentDifficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(ContentDifficulty difficulty) {
        this.difficulty = difficulty;
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

    public Set<TagEntity> getTags() {
        return tags;
    }

    public void setTags(Set<TagEntity> tags) {
        this.tags = tags;
    }

    public void addTag(TagEntity tag) {
        if (this.tags == null) {
            this.tags = new HashSet<>();
        }
        this.tags.add(tag);
    }

    public void removeTag(TagEntity tag) {
        if (this.tags != null) {
            tags.remove(tag);
        }
    }

    public Set<String> getCreators() {
        return creators;
    }

    public void setCreators(Set<String> creators) {
        this.creators = creators;
    }

    public Integer getNumberOfEnrolledUsers() {
        return numberOfEnrolledUsers;
    }

    public void setNumberOfEnrolledUsers() {
        if (this.numberOfEnrolledUsers == null) {
            this.numberOfEnrolledUsers = 0;
        }
    }

    public void enrollUser() {
        if (this.numberOfEnrolledUsers == null) {
            this.numberOfEnrolledUsers = 0;
        }
        this.numberOfEnrolledUsers++;
    }

    public void unEnrollUser() {
        this.numberOfEnrolledUsers--;
    }

    public String getIconURL() {
        return iconURL;
    }

    public void setIconURL(String iconURL) {
        this.iconURL = iconURL;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    public LocalDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public void update() {
        this.lastUpdatedAt = LocalDateTime.now();
    }
}
