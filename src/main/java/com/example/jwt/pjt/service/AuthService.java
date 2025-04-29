package com.example.jwt.pjt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jwt.pjt.dao.AuthRepository;
import com.example.jwt.pjt.domain.UserRequestDTO;
import com.example.jwt.pjt.domain.UserResponseDTO;
import com.example.jwt.pjt.domain.entity.UserEntity;
import com.example.jwt.pjt.util.JwtProvider;

@Service
public class AuthService {
    @Autowired
    private AuthRepository repository;
    @Autowired
    private JwtProvider provider;
    public UserResponseDTO loginService(UserRequestDTO params){
        System.out.println("service loginservice");
        String accToken =provider.generateAccToken(params.getEmail());
        String refToken = provider.generateReToken(params.getEmail());
        UserEntity entity = UserEntity.builder()
                                .email(params.getEmail())
                                .passwd(params.getPasswd())
                                .refreshToken(refToken)
                                .build();
        repository.save(entity);
        UserResponseDTO response= UserResponseDTO.builder()
                                    .accessToken(accToken)
                                    .refreshToken(refToken)
                                    .build();
        return response;
    }
}
