package com.example.board.service;

import com.example.board.dto.MemberDto;

public interface MemberService {

    // 회원가입
    public void register(MemberDto insertDto);

    // 회원수정, 회원 탈퇴 => default dtoToEntity, EntityToDto 만들어서 사용해야함

}
