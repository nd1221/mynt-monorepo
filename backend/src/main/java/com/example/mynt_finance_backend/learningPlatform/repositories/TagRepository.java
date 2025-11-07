package com.example.mynt_finance_backend.learningPlatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.TagEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, Integer> {

    @Query("""
            SELECT tagEntity
            FROM TagEntity tagEntity
            WHERE tagEntity.tag IN :tags
            """)
    List<TagEntity> findAllTagsByValue(@Param("tags") List<String> tags);
}
