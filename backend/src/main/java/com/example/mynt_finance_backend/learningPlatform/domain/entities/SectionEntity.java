package com.example.mynt_finance_backend.learningPlatform.domain.entities;

import com.example.mynt_finance_backend.learningPlatform.domain.entityInterfaces.Unit;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sections")
public class SectionEntity implements Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer position;

    @ManyToOne(
            targetEntity = CourseEntity.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "course_id")
    private CourseEntity course;

    @OneToMany(
            targetEntity = LessonEntity.class,
            mappedBy = "section",
            fetch = FetchType.LAZY
    )
    private Set<LessonEntity> lessons;

    @OneToOne(
            targetEntity = TestEntity.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "test_id")
    @Cascade(CascadeType.REMOVE)
    private TestEntity test;

    public SectionEntity() {}

    public SectionEntity(Integer id, String title, String description, Integer position, CourseEntity course, Set<LessonEntity> lessons, TestEntity test) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.position = position;
        this.course = course;
        this.lessons = lessons;
        this.test = test;
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

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public CourseEntity getCourse() {
        return course;
    }

    public void setCourse(CourseEntity course) {
        this.course = course;
    }

    public Set<LessonEntity> getLessons() {
        return lessons;
    }

    public void setLessons(Set<LessonEntity> lessons) {
        this.lessons = lessons;
    }

    public void addLesson(LessonEntity lesson) {
        if (this.lessons == null) {
            this.lessons = new HashSet<>();
        }
        this.lessons.add(lesson);
    }

    public void removeLesson(LessonEntity toRemove) {
        if (this.lessons != null) {
            this.lessons.remove(toRemove);
        }
    }

    public TestEntity getTest() {
        return test;
    }

    public void setTest(TestEntity test) {
        this.test = test;
    }

    public void removeTest() {
        this.test = null;
    }
}
