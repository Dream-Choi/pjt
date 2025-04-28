package com.example.jwt.pjt.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jwt.pjt.domain.entity.UserEntity;

@Repository
public interface AuthRepository extends JpaRepository<UserEntity, Long> {
    
}
