package com.example.jwt.pjt.dao;

import java.lang.StackWalker.Option;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.jwt.pjt.domain.entity.JpaSampleEntity;
//JPQL-update, delete
@Repository
public interface JpaSampleRepository extends JpaRepository<JpaSampleEntity, String> {
    public Optional<JpaSampleEntity> findByUserIdAndPasswd(String userId, String passwd);
    @Modifying
    @Query("update JpaSampleEntity j set j.passwd = :passwd, j.name = :name where j.userId = :userId")
    public void updateRow(  @Param("userId") String userId,
                            @Param("passwd") String passwd,
                            @Param("name") String name
    );
}
