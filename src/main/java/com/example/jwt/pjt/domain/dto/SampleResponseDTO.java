package com.example.jwt.pjt.domain.dto;

import com.example.jwt.pjt.domain.entity.JpaSampleEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SampleResponseDTO {
    
    public SampleResponseDTO(JpaSampleEntity entity) {
        this.userId = entity.getUserId();
        this.passwd = entity.getPasswd();
        this.name = entity.getName();
    }
    public SampleResponseDTO(String userId, String passwd, String name) {
        this.userId = userId;
        this.passwd = passwd;
        this.name = name;
    }
    private String userId;
    private String passwd;
    private String name;
    private Integer point;
    private String refreshToken;
    
}
