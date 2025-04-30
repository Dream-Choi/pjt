package com.example.jwt.pjt.service;

import java.util.Optional;

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
        //db에 무조건 저장
        /*UserEntity entity = UserEntity.builder()
                                .email(params.getEmail())
                                .passwd(params.getPasswd())
                                .refreshToken(refToken)
                                .build();
        repository.save(entity);*/
        //db에 있으면 갱신 없으면 저장
        Optional<UserEntity> entity = repository.findByEmail(params.getEmail());
        if(entity.isPresent()){
            //refreshToken이 다르면 갱신
            UserEntity result = entity.get();
            UserResponseDTO response= UserResponseDTO.builder()
                                    .email(result.getEmail())
                                    .passwd(result.getPasswd())
                                    .accessToken(accToken)
                                    .refreshToken(result.getRefreshToken())
                                    .build();
            return response;
        }else{
            //db에 없으면 저장
            UserEntity newEntity = UserEntity.builder()
                                    .email(params.getEmail())
                                    .passwd(params.getPasswd())
                                    .refreshToken(refToken)
                                    .build();
            repository.save(newEntity);
        }
        UserResponseDTO response= UserResponseDTO.builder()
                                    .email(params.getEmail())
                                    .passwd(params.getPasswd())
                                    .accessToken(accToken)
                                    .refreshToken(refToken)
                                    .build();
        return response;
    }
    public String renewService(String token) throws Exception{
        System.out.println("service renew token "+token);
        System.out.println("service renew");
        Optional<UserEntity> entity = repository.findByRefreshToken(token);
        if(entity.isPresent()){
            String email =provider.renewToken(entity.get().getRefreshToken());
            String newToken = provider.generateAccToken(email);
            return newToken;
        }else{
            System.out.println("service renew error");
            throw new Exception("재발급실패");
        }
    }
}
