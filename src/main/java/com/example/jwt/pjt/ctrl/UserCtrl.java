package com.example.jwt.pjt.ctrl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwt.pjt.domain.MemberRequestDTO;
import com.example.jwt.pjt.domain.PostRequestDTO;
import com.example.jwt.pjt.domain.PostResponseDTO;
import com.example.jwt.pjt.domain.UserRequestDTO;
import com.example.jwt.pjt.service.UserService;

import oracle.jdbc.proxy.annotation.Post;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/user")
public class UserCtrl {
    @Autowired
    private UserService service;
    @PostMapping("/userCreate")
    public ResponseEntity<Void> createUser(@RequestBody MemberRequestDTO param) {
        System.out.println("UserCtrl.createUser() called");
        service.createUserService(param);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/post")
    public ResponseEntity<PostResponseDTO> createPost(@PathVariable(name = "id")String id,  @RequestBody PostRequestDTO param) {
        System.out.println("UserCtrl.createPost() called");
        PostResponseDTO response = service.createPostService(id, param);
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/{id}/list")
    public ResponseEntity getUserPosts(@PathVariable(name = "id")String id) {
        System.out.println("UserCtrl.getUserPost() called");
        List<PostResponseDTO> lst=service.getUserPostsService(id);
        return ResponseEntity.ok().body(lst);
    }
    
    
}
