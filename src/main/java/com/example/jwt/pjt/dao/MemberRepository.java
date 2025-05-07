package com.example.jwt.pjt.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jwt.pjt.domain.entity.MemberEntity;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, String> {
    // Custom query methods can be defined here if needed
    // For example, findByEmail, findByUsername, etc.
    
}
