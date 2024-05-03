package com.example.board.service;

import com.example.board.dto.MemberDto;

public interface MovieUserService {

    // 회원가입
    String register(MemberDto insertDto) throws IllegalStateException;

    // 닉네임 수정
    void nickNameUpdate(MemberDto upMemberDto);

    // 비밀번호 수정
    void passwordUpdate();
}
