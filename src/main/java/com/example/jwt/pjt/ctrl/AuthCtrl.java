package com.example.jwt.pjt.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwt.pjt.domain.UserRequestDTO;
import com.example.jwt.pjt.domain.UserResponseDTO;
import com.example.jwt.pjt.service.AuthService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthCtrl {
    @Autowired
    private AuthService service;
    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@RequestBody UserRequestDTO params){
        System.out.println("ctrl login");
        System.out.println("ctrl login param "+params);
        UserResponseDTO response = service.loginService(params);
        System.out.println("ctrl login response"+response);
        return ResponseEntity.ok().body(response);
    }
}
