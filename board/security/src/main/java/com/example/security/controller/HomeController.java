package com.example.security.controller;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

// 스프링 시큐리티 를 프로젝트에 적용 하면

// 1. 모든 요청은 로그인 페이지를 거치도록 설정 됨
//         => defalut 로그인 페이지가 동작이 됨
//         => test User  도 하나 생성
//         username(id) : user
//         password : 컨트롤로 로딩 시 변경 됨

// 2. 로그인 작업
//      => 로그인 성공시 원래 시작 했던 페이지로 되돌려 보내줌

//      => http://localhonst:8080/security/admin 요청
//      => 권한 부여 받았는지 확인함
//      => http://localhonst:8080/login 페이지 보여주기 
//      => 로그인 입력 후 인가된 사용자라면(인가된 정보를 SecurityContext 저장)
//     *******************************  => id 역할을 하는 요소의 이름은 반드시 반드시 username 으로 해야 한다 (변경은 가능하지만 걍 이렇게 해라) *******************************
//     *******************************  => 비밀변호 역할을 하는 요소의 이름은 반드시 password 이다 (벼변경은 가능)                               ******************************* 
//     *******************************  => get 을 제외한 모든 method 는 csrf 값을 포함 해야 한다(csrf disable 하지 않는다면) 

//      => 시작 했던 페이지로 이동 => http://localhonst:8080/security/admin  

// 3. 로그 아웃도 defalut LoginOutPage 가 동작
//      => http://localhonst:8080/logout => 자동 응담(session 제거 ,  쿠키 제거) 처리
//      =>
//      =>
//      =>

@Controller
@Log4j2
public class HomeController {

    @GetMapping("/")
    public String getHome() {
        log.info("home 요청");
        return "home";
    }

    @ResponseBody
    @GetMapping("/auth")
    public Authentication getAuth() {

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String username = authentication.getName();
        Object principal = authentication.getPrincipal();

        Collection<? extends GrantedAuthority> getAuthorities = authentication.getAuthorities();

        log.info("--------------------------------");
        log.info("username {}", username);
        log.info("principal {}", principal);
        log.info("getAuthorities {}", getAuthorities);
        return authentication;
    }

}
