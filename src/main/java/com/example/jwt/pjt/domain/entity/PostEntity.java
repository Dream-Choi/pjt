package com.example.jwt.pjt.domain.entity;

import jakarta.persistence.Id;

import com.example.jwt.pjt.domain.PostRequestDTO;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
데이터베이스 테이블과 java 객체간의 메핑을 도와주는
@Table -
@Entity- 반드시 기본키를(@ID) 설정해야함
 */
@Table(name="siat_post_tbl")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    private String title;
    private String content;
    
    public void updatePost(PostRequestDTO param) {
        this.title = param.getTitle();
        this.content = param.getContent();
    }
    
}
