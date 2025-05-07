package com.example.jwt.pjt.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jwt.pjt.domain.entity.PostEntity;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
    // Custom query methods can be defined here if needed
    // For example, findByTitle, findByAuthor, etc.
    
}
