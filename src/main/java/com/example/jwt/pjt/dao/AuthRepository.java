package com.example.jwt.pjt.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jwt.pjt.domain.entity.UserEntity;

import oracle.net.aso.f;

@Repository
public interface AuthRepository extends JpaRepository<UserEntity, Long> {
    public Optional<UserEntity> findByEmail(String email);
    public Optional<UserEntity> findByRefreshToken(String token);
}
