package com.example.board.contorller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.board.dto.MemberDto;
import com.example.board.dto.PageRequestDto;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    @PreAuthorize("permitAll()")
    @GetMapping("/login")
    public void getMethodName(PageRequestDto requestDto) {

        log.info("로그인 페이지 요청");

    }

    @PreAuthorize("permitAll()")
    @GetMapping("/register")
    public void getRegister(PageRequestDto requestDto, MemberDto memberDto) {

        log.info("회원가입 페이지 요청");

    }

    @PostMapping("register")
    public String postRegister(@RequestBody String entity) {

        return "redirect:/member/login";
    }

}
