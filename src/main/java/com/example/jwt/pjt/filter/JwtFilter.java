package com.example.jwt.pjt.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import oracle.net.aso.c;
/*
 브라우저 요청방식 4가지(GET, POST, PUT, DELETE) 요청전에 자동으로 OPTION(preflight request) 요청을 먼저 보내서 필터를 통해서 
 jwt검사를 하게되면 CORS검증이 안됨
 */
@Component
public class JwtFilter implements Filter{
    //private final Key key = Keys.hmacShaKeyFor("your-256-bit-secret-key-which-is-long-enough-to-meet-requirements".getBytes(StandardCharsets.UTF_8));
    // Implement the filter logic here
    // For example, you can override the doFilter method to process JWT tokens
    // and validate requests.
    @Value("${jwt.secret}")
    private String secret;
    private Key key=null;
    @PostConstruct
    private void init() { 
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Example: Print a message to indicate the filter is working
        System.out.println("JWT Filter is processing the request");
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String path = req.getRequestURI();
        System.out.println("Request Path: " + path);
        String method = req.getMethod();
        System.out.println("Request Method: " + method);
        if("OPTIONS".equalsIgnoreCase(method)) {
            res.setStatus(HttpServletResponse.SC_OK);
            //res.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:3000");
            //res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            //res.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
            chain.doFilter(request, response);
            return;
        }
        if(isPassPath(path)) {
            System.out.println("인증 필요없는 경로는 JWT 검증 제외");
            chain.doFilter(request, response);
            return;
        }
        String authHeader = req.getHeader("Authorization");
        System.out.println("Authorization Header: " + authHeader);
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("if true Authorization header 확인");
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        String token = authHeader.substring(7);
        System.out.println("JWT Token: " + token);
        try{
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
            System.out.println("JWT 검증 성공 컨트롤러로 이동");
            chain.doFilter(request, response);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("토큰의 문제가 생김.");
            System.out.println("JWT 검증 실패");
            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
            res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            res.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
            res.setHeader("Access-Control-Allow-Credentials", "true");
            res.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
            return;
        }
    }
    // 특정 URL 패턴을 JWT 검증 제외.
    public boolean isPassPath(String path) {
        return path.startsWith("/swagger-ui") || 
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/auth") ||
                path.startsWith("/swagger-resources") ||
                path.startsWith("/h2-console")||
                path.startsWith("/user");
    }

    
}
