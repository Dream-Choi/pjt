package com.example.jwt.pjt.domain;

import com.example.jwt.pjt.domain.entity.PostEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@Setter
@Getter
@ToString
@NoArgsConstructor
public class PostResponseDTO {
    public PostResponseDTO(PostEntity entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
    }
    private Long id;
    private String title;
    private String content;
    
}
