package com.example.movie.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.movie.dto.AuthMemberDto;
import com.example.movie.dto.MemberDto;
import com.example.movie.dto.PageRequestDto;
import com.example.movie.dto.PasswordChangeDto;
import com.example.movie.entity.Member;
import com.example.movie.repository.MemberRepository;
import com.example.movie.service.MovieServiceImpl;
import com.example.movie.service.MovieUserServiceImpl;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequestMapping("/member")
@Log4j2
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MovieUserServiceImpl service;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public void getLogin(@ModelAttribute("requestDto") PageRequestDto pageRequestDto) {
        log.info("로그인 폼 요청");
    }

    // @ModelAttribute("requestDto") PageRequestDto pageRequestDto 이거 항상 따라가게 하자
    @PreAuthorize("isAuthenticated()") // 로그인 해야 가능함을 나타냄
    @GetMapping("/profile")
    public void getProfile(@ModelAttribute("requestDto") PageRequestDto pageRequestDto) {

        log.info("프로필 요청");

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/edit")
    public String getMethodName(@ModelAttribute("requestDto") PageRequestDto pageRequestDto) {

        log.info("닉네임, 비밀번호 수정");

        return "/member/edit-profile";

    }

    // /edit/nickname
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/edit/nickname")
    public String nicknamePost(MemberDto memberDto, HttpSession session) {

        service.nickNameUpdate(memberDto);
        // SecurityContent 안에 저장된 Authentication 변경되지않음
        // 1) 현재 세션 제거 => 재로그인
        // session.invalidate(); 세션 날려버리기

        // 2) Authentication 업데이트

        SecurityContext context = SecurityContextHolder.getContext();

        Authentication authentication = context.getAuthentication();
        AuthMemberDto authMemberDto = (AuthMemberDto) authentication.getPrincipal();
        authMemberDto.getMemberDto().setNickname(memberDto.getNickname());
        // 다시 셋팅
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/member/profile";

    }

    // /edit/password

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/edit/password")
    public String passwordPost(HttpSession session, PasswordChangeDto pChangeDto, RedirectAttributes rttr) {

        // 현재 비번이 틀리면 /member/edit

        try {

            service.passwordUpdate(pChangeDto);

        } catch (IllegalStateException e) {

            rttr.addFlashAttribute("error", e.getMessage());

            return "redirect:/member/edit";

        }

        session.invalidate();

        return "redirect:/member/login";
    }

    @GetMapping("/register")
    public void getRegister(@ModelAttribute("requestDto") PageRequestDto pageRequestDto, MemberDto memberDto) {

        log.info("회원가입 폼 요청");

    }

    @PostMapping("/register")
    public String postMethodName(@Valid MemberDto dto, BindingResult result, RedirectAttributes rttr,
            @ModelAttribute("requestDto") PageRequestDto pageRequestDto) {
        log.info("회원가입 요청{}", dto);

        if (result.hasErrors())
            return "/member/register";

        String email = "";

        try {
            email = service.register(dto);

        } catch (Exception e) {
            rttr.addFlashAttribute("error", e.getMessage());
            return "redirect:/member/register";

        }

        rttr.addFlashAttribute("email", email);

        return "redirect:/member/login";
    }

    @GetMapping("/leave")
    public void getLeaveForm(MemberDto leaveMemberDto, @ModelAttribute("requestDto") PageRequestDto pageRequestDto) {
        log.info("회원탈퇴");

        //

    }

    @PostMapping("/leave")
    public String postMethodName(MemberDto leaveMemberDto, RedirectAttributes rttr, HttpSession session,
            @ModelAttribute("requestDto") PageRequestDto pageRequestDto) {
        log.info("회원탈퇴{}", leaveMemberDto);
        try {
            service.leave(leaveMemberDto);

        } catch (Exception e) {
            rttr.addFlashAttribute("error", "이메일이나 비밀번호를 확인해주세요");

        }

        session.invalidate();

        return "redirect:/";
    }

}
