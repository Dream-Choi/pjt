package com.example.jwt.pjt.ctrl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwt.pjt.dao.JpaSampleRepository;
import com.example.jwt.pjt.domain.entity.JpaSampleEntity;
import com.example.jwt.pjt.util.JwtProvider;

import jakarta.transaction.Transactional;
import oracle.net.aso.j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/jpa")
public class JpaCtrl {
    @Autowired
    private JpaSampleRepository jpaSampleRepository;
    @Autowired
    private JwtProvider provider;
    @PostMapping("/sign-up")
    public String signUp(@RequestBody Map<String, String> param) {
        System.out.println("JpaCtrl.signUp() called");
        JpaSampleEntity entity = JpaSampleEntity.builder()
                                    .userId(param.get("userId"))
                                    .passwd(param.get("passwd"))
                                    .name(param.get("name"))
                                    .point(Integer.parseInt(param.get("point")))
                                    .build();
        jpaSampleRepository.save(entity);
        return null;
    }
    @PostMapping("/sign-in")
    @Transactional
    public String signIn(@RequestBody Map<String, String> param) {
        System.out.println("JpaCtrl.signIn() called");
        String accToken =provider.generateAccToken(param.get("userId"));
        String refToken = provider.generateReToken(param.get("userId"));
        JpaSampleEntity entity=jpaSampleRepository
        .findByUserIdAndPasswd(param.get("userId"), param.get("passwd"))
        .orElseThrow(()->new RuntimeException("not found"));
        entity.setRefreshToken(refToken);
        return null;
    }
    /*
     Dirty cheking
     엔티티의 변경사항을 자동으로 감지하여 DB에 반영하는 JPA의 기능
     */
    @PutMapping("/update")
    @Transactional
    public String update(@RequestBody Map<String, String> param) {
        System.out.println("JpaCtrl.update() called");
        // JpaSampleEntity entity = JpaSampleEntity.builder()
        //                             .userId(param.get("userId"))
        //                             .passwd(param.get("passwd"))
        //                             .name(param.get("name"))
        //                             .build();
        // jpaSampleRepository.save(entity);
        // JpaSampleEntity entity = jpaSampleRepository
        //         .findById(param.get("userId"))
        //         .orElseThrow(() -> new RuntimeException("not found"));
        // entity.setPasswd(param.get("passwd"));
        // entity.setName(param.get("name"));
        jpaSampleRepository.updateRow(
            param.get("userId"),
            param.get("passwd"),
            param.get("name")
        );
        return null;
    }
}
