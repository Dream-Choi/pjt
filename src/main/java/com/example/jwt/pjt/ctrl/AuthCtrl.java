package com.example.jwt.pjt.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwt.pjt.domain.UserRequestDTO;
import com.example.jwt.pjt.domain.UserResponseDTO;
import com.example.jwt.pjt.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
public class AuthCtrl {
    @Autowired
    private AuthService service;
    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@RequestBody UserRequestDTO params){
        System.out.println("ctrl login");
        System.out.println("ctrl login param "+params);
        UserResponseDTO response = service.loginService(params);
        System.out.println("ctrl login response"+response);
        return ResponseEntity.ok()
                .header("Authorization", "Bearer "+response.getAccessToken())
                .header("Refresh-Token", response.getRefreshToken())
                .body(response);
    }
    @PostMapping("/renew")
    public ResponseEntity<?> renewToken(HttpServletRequest request){
        System.out.println("ctrl renew");
        String header = request.getHeader("Authorization");
        System.out.println("ctrl renew header "+header);
        String token = header.substring(7);
        System.out.println("ctrl renew token "+token);
    try{
        String newAccessToken = service.renewService(token);
        System.out.println("ctrl renew newAccessToken "+newAccessToken);
        return ResponseEntity.ok()
                .header("Authorization", "Bearer "+newAccessToken)
                .build();
        }catch(Exception e){
            System.out.println("ctrl renew error ");
            return ResponseEntity
                .status(HttpStatus.FORBIDDEN).body("재발급실패");
        }
    }
}
