package com.example.mynt_finance_backend.learningPlatform.domain.entities;

import com.example.mynt_finance_backend.learningPlatform.domain.entityInterfaces.Unit;
import jakarta.persistence.*;
import org.hibernate.Length;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "lessons")
public class LessonEntity implements Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer position;

    // There is some bug in Hibernate 6.5 where Hibernate cannot read columnDefinition="TEXT" properly and tries to parse it to a long
    // Only fix I have found so far is to specify the JdbcTypeCode as LONG32VARCHAR and set the length in @Column, this configuration
    // ensures content has SQL type of 'text' but I don't really know why
    @Lob
    @Column(
            nullable = true, // TEMPORARY <<============================================================================
            length = Length.LOB_DEFAULT
    )
    @JdbcTypeCode(SqlTypes.LONG32VARCHAR)
    private String content;

    @ManyToOne(
            targetEntity = SectionEntity.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "section_id")
    private SectionEntity section;

    @OneToMany(
            targetEntity = QuestionEntity.class,
            mappedBy = "lesson",
            fetch = FetchType.LAZY
    )
    private Set<QuestionEntity> questions;

    @Column(nullable = true)
    private int duration;

    public LessonEntity() {}

    public LessonEntity(Integer id, String title, String description, Integer position, String content, SectionEntity section, Set<QuestionEntity> questions, int duration) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.position = position;
        this.content = content;
        this.section = section;
        this.questions = questions;
        this.duration = duration;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public SectionEntity getSection() {
        return section;
    }

    public void setSection(SectionEntity section) {
        this.section = section;
    }

    public Set<QuestionEntity> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<QuestionEntity> questions) {
        this.questions = questions;
    }

    public void addQuestion(QuestionEntity question) {
        if (this.questions == null) {
            this.questions = new HashSet<>();
        }
        this.questions.add(question);
    }

    public void removeQuestion(QuestionEntity question) {
        if (this.questions != null) {
            this.questions.remove(question);
        }
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
