package com.example.jwt.pjt.service;



import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.jwt.pjt.dao.MemberRepository;
import com.example.jwt.pjt.dao.PostRepository;
import com.example.jwt.pjt.domain.MemberRequestDTO;
import com.example.jwt.pjt.domain.PostRequestDTO;
import com.example.jwt.pjt.domain.PostResponseDTO;
import com.example.jwt.pjt.domain.UserRequestDTO;
import com.example.jwt.pjt.domain.entity.MemberEntity;
import com.example.jwt.pjt.domain.entity.PostEntity;





@Service
public class UserService {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PostRepository postRepository;
    public void createUserService(MemberRequestDTO param) {
        System.out.println("UserService.createUser() called");
        MemberEntity entity = MemberEntity.builder()
                                .email(param.getEmail())
                                .passwd(param.getPasswd())
                                .build();
        memberRepository.save(entity);
    }
    public PostResponseDTO createPostService(String id, PostRequestDTO param) {
        System.out.println("UserService.createPost() called");
        Optional<MemberEntity> op = memberRepository.findById(id);
        PostEntity post = PostEntity.builder()
                                .title(param.getTitle())
                                .content(param.getContent())
                                .build();
        if(op.isPresent()){
            op.get().addPost(post);
            memberRepository.save(op.get());
            return PostResponseDTO.builder()
                                .title(param.getTitle())
                                .content(param.getContent())
                                .build();
        }
        return null;
    }
    public List<PostResponseDTO> getUserPostsService(String id) {
        System.out.println("UserService.getUserPosts() called");
        Optional<MemberEntity> op = memberRepository.findById(id);
        /*List<PostResponseDTO> lst = new ArrayList<>();
        if (op.isPresent()){
            List<PostEntity> posts = op.get().getPosts();
            for(PostEntity entity : posts){
                PostResponseDTO response = PostResponseDTO.builder()
                                        .id(entity.getId())
                                        .title(entity.getTitle())
                                        .content(entity.getContent())
                                        .build();
                lst.add(response);
            }
        }*/
        if(op.isPresent()){
            return op.get().getPosts().stream()
                .map(PostResponseDTO::new)
                .toList();
        }
        return null;
    }
    @Transactional
    public void updatePostService(String email, PostRequestDTO param) {
        System.out.println("UserService.updatePost() called");
        System.out.println("execute query1");
        MemberEntity member = memberRepository.findById(email)
                .orElseThrow(()-> new RuntimeException("User not found"));
        System.out.println("execute query2");
        Optional<PostEntity> post = member.findPost(param.getId());
        System.out.println("post size: " + member.getPosts().size());
        System.out.println("execute query3");
        post.get().updatePost(param);
    }
    public void deletePostService(String email, Long postId) {
        System.out.println("UserService.deletePost() called");
        MemberEntity member = memberRepository.findById(email)
                .orElseThrow(()-> new RuntimeException("User not found"));
        Optional<PostEntity> post = member.findPost(postId);
        if(post.isPresent()){
            member.getPosts().remove(post.get());
            memberRepository.save(member);
        }
    }
}
