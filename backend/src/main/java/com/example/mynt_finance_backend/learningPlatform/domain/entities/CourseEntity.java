package com.example.mynt_finance_backend.learningPlatform.domain.entities;

import com.example.mynt_finance_backend.learningPlatform.domain.entityEnums.ContentDifficulty;
import com.example.mynt_finance_backend.learningPlatform.domain.entityInterfaces.Enrollable;
import com.example.mynt_finance_backend.learningPlatform.domain.entityInterfaces.Unit;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "courses")
public class CourseEntity implements Unit, Enrollable {

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

    @OneToMany(
            targetEntity = SectionEntity.class,
            mappedBy = "course",
            fetch = FetchType.LAZY
    )
    private Set<SectionEntity> sections;

    @ManyToMany(
            targetEntity = TagEntity.class,
            mappedBy = "courses",
            fetch = FetchType.LAZY
    )
    private Set<TagEntity> tags;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "course_creators",
            joinColumns = @JoinColumn(
                    name = "course_id"
            )
    )
    @Column(name = "creator")
    private Set<String> creators;

    private Integer numberOfEnrolledUsers;

    private String iconURL;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime lastUpdatedAt;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "course_objectives",
            joinColumns = @JoinColumn(
                    name = "course_id"
            )
    )
    @Column(name = "objective")
    private List<String> objectives;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "course_requirements",
            joinColumns = @JoinColumn(
                    name = "course_id"
            )
    )
    @Column(name = "requirements")
    private List<String> requirements;

    public CourseEntity() {}

    public CourseEntity(Integer id, String title, String description, ContentDifficulty difficulty, Set<SectionEntity> sections, Set<TagEntity> tags, Set<String> creators, Integer numberOfEnrolledUsers, String iconURL, LocalDateTime createdAt, LocalDateTime lastUpdatedAt, List<String> objectives, List<String> requirements) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
        this.sections = sections;
        this.tags = tags;
        this.creators = creators;
        this.numberOfEnrolledUsers = numberOfEnrolledUsers;
        this.iconURL = iconURL;
        this.createdAt = createdAt;
        this.lastUpdatedAt = lastUpdatedAt;
        this.objectives = objectives;
        this.requirements = requirements;
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

    public Set<SectionEntity> getSections() {
        return sections;
    }

    public void setSections(Set<SectionEntity> sections) {
        this.sections = sections;
    }

    public void addSection(SectionEntity section) {
        if (this.sections == null) {
            this.sections = new HashSet<>();
        }
        this.sections.add(section);
    }

    public void removeSection(SectionEntity toRemove) {
        if (this.sections != null) {
            sections.remove(toRemove);
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

    public List<String> getObjectives() {
        return objectives;
    }

    public void setObjectives(List<String> objectives) {
        this.objectives = objectives;
    }

    public List<String> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<String> requirements) {
        this.requirements = requirements;
    }

    public int getNumberOfLessons() {
        return this.sections.stream()
                .mapToInt(section -> section.getLessons().size())
                .sum();
    }
}
