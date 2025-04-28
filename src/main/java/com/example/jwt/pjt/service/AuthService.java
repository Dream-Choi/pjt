package com.example.jwt.pjt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jwt.pjt.dao.AuthRepository;
import com.example.jwt.pjt.domain.UserRequestDTO;
import com.example.jwt.pjt.domain.UserResponseDTO;
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
        UserResponseDTO response= UserResponseDTO.builder().accessToken(accToken).refreshToken(refToken).build();
        return response;
    }
}
