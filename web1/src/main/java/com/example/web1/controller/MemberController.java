package com.example.web1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.web1.dto.AddDto;
import com.example.web1.dto.LoginDto;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@Log4j2
@RequestMapping("/member")

public class MemberController {
    @GetMapping("/login")
    public void login(LoginDto loginDto) {
        log.info("로그인 페이지 요청 요청");
    }

    // @PostMapping("/login")
    // public void loginPost(String email, String name) {
    // log.info("로그인 정보 가져오기");
    // log.info("email {}", email);
    // log.info("name {}", name);

    // }

    // @PostMapping("/login")
    // public String loginPost(@ModelAttribute("dto") LoginDto dto,
    // @ModelAttribute("page") int page, Model model) {
    // log.info("/login post 요청");
    // log.info("email{}", dto.getEmail());
    // log.info("name{}", dto.getName());
    // log.info("page {} ", page);

    // // model.addAttribute("page", page); == @ModelAttribute("page") 결국 둘은 같은 역할을
    // 한다

    // return "/member/info";

    // }

    // 에러가 있다면 BindingResult 객체로 자동으로 담긴다 에러가 있다면
    // return "/member/login";를 통해 다시 로그인 시도를 시킴

    @PostMapping("/login")
    public String loginPost(@Valid LoginDto mDto, BindingResult result) {
        log.info("로그인 정보 가져오기");
        log.info("email {}", mDto.getEmail());
        log.info("name {}", mDto.getName());

        // 유효성 검증을 통과하지 못한다면
        if (result.hasErrors()) {
            return "/member/login";

        }
        return "/member/login";

    }
}

// 데이터 보내기
// mvc 모델 2의 경우

// request.setAttribute("이름",값) 으로 세팅 하고
// ${} 를 이용해서 jsp 로 보여주게 함

// 하지만 스프링 쪽에서는 이러한 역할을 똑같이 수행하는 Model 이라는 객채를 이미 만들어 놓음