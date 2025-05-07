package com.example.jwt.pjt.domain;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.example.jwt.pjt.domain.entity.PostEntity;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDTO {
    private Long id;
    private String email;
    private String passwd;
    private String accessToken;
    private String refreshToken;
    private List<PostResponseDTO> posts;
}
